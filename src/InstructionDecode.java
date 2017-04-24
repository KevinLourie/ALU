/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /**
     * Instruction is located here
     */
    RegisterBank registerBank;

    /**
     * Decodes instruction
     */
    Decoder decoder;

    Bus<Integer, Byte> opcodeBus;

    Bus<Integer, Byte> regAddressBus1;

    Bus<Integer, Byte> regAddressBus2;

    Bus<Integer, Byte> regAddressBus;

    /**
     * Instruction
     */
    Register<Integer> instructionRegister;

    /**
     * Location of instruction
     */
    Register<Integer> nextPC;

    /**
     * Constructor
     * @param registerBank register bank
     */
    InstructionDecode(RegisterBank registerBank) {
        opcodeBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)(input.read() >> 26);
            }
        };
        regAddressBus1 = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >> 21) & 0x1F);
            }
        };
        regAddressBus2 = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >> 16) & 0x1F);
            }
        };
        regAddressBus = new Bus<Integer, Byte>() {
            @Override
            public Byte read() {
                return (byte)((input.read() >> 11) & 0x1F);
            }
        };
        this.registerBank = registerBank;
        decoder = new Decoder();
        instructionRegister = new Register<>("Data Register", 0);
        nextPC = new Register<>("Next PC", 0);
    }

    /**
     * Initilize data register and address register
     * @param dataInput data register input
     * @param nextPCInput address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPCInput) {
        regAddressBus1.init(instructionRegister.getOutput());
        regAddressBus2.init(instructionRegister.getOutput());
        regAddressBus.init(instructionRegister.getOutput());
        opcodeBus.init(instructionRegister.getOutput());
        instructionRegister.init(dataInput);
        nextPC.init(nextPCInput);
        registerBank.init(regAddressBus1, regAddressBus2, regAddressBus, dataInput);
        decoder.init(null);
    }

    public Output<Integer> getAddressRegisterOutput() {
        return nextPC.getOutput();
    }

    /**
     * Cycle register bank and registers
     */
    public void cycle() {
        registerBank.cycle();
        instructionRegister.cycle();
        nextPC.cycle();
    }
}
