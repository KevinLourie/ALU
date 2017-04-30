/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /**
     * Register bank
     */
    private RegisterBank registerBank;

    /**
     * Returns microcode for instruction
     */
    private Decoder decoder;

    /**
     * Extracts opcode from instruction
     */
    private Bus<Integer, Byte> opcodeBus;

    /**
     * Extracts S register selection from instruction
     */
    private Bus<Integer, Byte> sSelectorBus;

    /**
     * Extracts T register selection from instruction
     */
    private Bus<Integer, Byte> tSelectorBus;

    /**
     * Extracts D register selection from instruction
     */
    private Bus<Integer, Byte> dSelectorBus;

    /**
     * Extracts immediate from instruction
     */
    private Bus<Integer, Integer> immediateBus;

    /**
     * Determine if branch condition is true by comparing data in S and T
     */
    private Comparator comparator;

    /**
     * Holds instruction
     */
    private Register<Integer> instructionRegister;

    /**
     * Location of next instruction
     */
    private Register<Integer> nextPc;

    /**
     * Shifting the immediate left by 2 bits for branch instruction because instructions are 4 bytes
     */
    private Bus<Integer, Integer> shiftLeft;

    private Bus<Integer, Byte> shamtBus;

    private Bus<Integer, Byte> functBus;

    /**
     * Constructor
     * @param registerBank register bank
     */
    InstructionDecode(RegisterBank registerBank, Cycler cycler) {
        comparator = new Comparator();
        opcodeBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)(input.read() >>> 26);
            }
        };
        sSelectorBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >>> 21) & 0x1F);
            }
        };
        tSelectorBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >>> 16) & 0x1F);
            }
        };
        dSelectorBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >>> 11) & 0x1F);
            }
        };
        immediateBus = new Bus<Integer, Integer>() {
            @Override
            public Integer read() {
                // Extract the bottom 16 bits and sign extend it to a 32-bits
                return (int)(short)(input.read() & 0xFFFF);
            }
        };
        shamtBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >>> 6) & 0x1F);
            }
        };
        functBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)(input.read() & 0x3F);
            }
        };
        this.registerBank = registerBank;
        decoder = new Decoder();
        instructionRegister = new Register<>("IR", 0, cycler);
        nextPc = new Register<>("Next PC", 0, cycler);
        shiftLeft = new Bus<Integer, Integer>() {
            @Override
            public Integer read() {
                return input.read() << 2;
            }
        };
    }

    /**
     * Initilize data register and address register
     * @param dataInput data register input
     * @param nextPCInput address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPCInput) {
        // TODO: add correct enable inputs
        shiftLeft.init(immediateBus);
        comparator.init(registerBank.getSOutput(), registerBank.getTOutput());
        sSelectorBus.init(instructionRegister.getOutput());
        tSelectorBus.init(instructionRegister.getOutput());
        dSelectorBus.init(instructionRegister.getOutput());
        opcodeBus.init(instructionRegister.getOutput());
        immediateBus.init(instructionRegister.getOutput());
        instructionRegister.init(dataInput, null);
        nextPc.init(nextPCInput, null);
        registerBank.initRead(sSelectorBus, tSelectorBus);
        decoder.init(null, null);
    }

    public Output<Integer> getNextPcOutput() {
        return nextPc.getOutput();
    }
}
