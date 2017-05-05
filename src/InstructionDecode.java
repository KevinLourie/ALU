/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /** Register bank */
    private RegisterBank registerBank;

    /** Returns microcode for instruction */
    private Decoder decoder;

    /** Extracts opcode from instruction */
    private Bus<Integer, Byte> opcodeBus = new OpcodeBus();

    /** Extracts S bus from instruction */
    private Bus<Integer, Byte> sSelectorBus = new SSelectorBus();

    /** Extracts T bus from instructon */
    private Bus<Integer, Byte> tSelectorBus = new TSelectorBus();

    /** Extracts D register selection from instruction*/
    private Bus<Integer, Byte> dSelectorBus = new DSelectorBus();

    /** Extracts immediate from instruction */
    private Bus<Integer, Integer> immediateBus = new ImmediateBus();

    /**Determine if branch condition is true by comparing data in S and T */
    private Comparator comparator;

    /** Holds instruction */
    private Register<Integer> instructionRegister;

    /** Location of next instruction*/
    private Register<Integer> nextPc;

    /** Shifting the immediate left by 2 bits for branch instruction because instructions are 4 bytes */
    private Bus<Integer, Integer> shiftLeft = new ShiftLeft();

    /** Contains the amount of bits to shift */
    private Bus<Integer, Byte> shamtBus = new ShamtBus();

    /** Contains the register function. Used as index into functMicroinstructions for register instructions*/
    private Bus<Integer, Byte> functBus = new FunctBus();

    /** Chooses between T and D register to write back to */
    private Multiplexer<Byte> wbSelectorMux;

    /**
     * Constructor
     * @param registerBank register bank
     */
    InstructionDecode(RegisterBank registerBank, Cycler cycler) {
        comparator = new Comparator();
        wbSelectorMux = new Multiplexer<>();
        instructionRegister = new Register<>("IR", 0, cycler);
        shiftLeft.setInput(immediateBus);
        functBus.setInput(instructionRegister.getOutput());
        comparator.init(registerBank.getSOutput(), registerBank.getTOutput());
        sSelectorBus.setInput(instructionRegister.getOutput());
        tSelectorBus.setInput(instructionRegister.getOutput());
        dSelectorBus.setInput(instructionRegister.getOutput());
        opcodeBus.setInput(instructionRegister.getOutput());
        immediateBus.setInput(instructionRegister.getOutput());
        registerBank
                .setAddressSInput(sSelectorBus)
                .setAddressTInput(tSelectorBus);
        decoder = new Decoder();
        decoder.init(opcodeBus, functBus);
        this.registerBank = registerBank;
        nextPc = new Register<>("Next PC", 0, cycler);

        // Internal wiring
        wbSelectorMux
                .setIndexInput(decoder.getWbSelectorMuxIndexOutput())
                .setInputs(tSelectorBus, dSelectorBus);
    }

    /**
     * Initialize instruction register
     * @param instructionInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setInstructionInput(Output<Integer> instructionInput) {
        instructionRegister.setInput(instructionInput);
        return this;
    }

    /**
     * Initialize program counter
     * @param nextPCInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setNextPcInput(Output<Integer> nextPCInput) {
        nextPc.setInput(nextPCInput);
        return this;
    }

    public Output<Integer> getSOutput() {
        return registerBank.getSOutput();
    }

    public Output<Integer> getTOutput() {
        return registerBank.getTOutput();
    }

    public Output<Integer> getCOutput() {
        return immediateBus;
    }

    public Output<Integer> getAluMuxIndexOutput() {
        return decoder.getAluMuxIndexOutput();
    }

    public Output<Byte> getWbSelectorOutput() {
        return null;
    }

    public Output<Byte> getAluOpOutput() {
        return decoder.getAluOpOutput();
    }

    private static class SSelectorBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)((input.read() >>> 21) & 0x1F);
        }
    }

    private static class TSelectorBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)((input.read() >>> 16) & 0x1F);
        }
    }

    private static class DSelectorBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)((input.read() >>> 11) & 0x1F);
        }
    }

    private static class OpcodeBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)(input.read() >>> 26);
        }
    }

    private static class FunctBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)(input.read() & 0x3F);
        }
    }

    private static class ImmediateBus extends Bus<Integer, Integer> {
        @Override
        public Integer read() {
            // Extract the bottom 16 bits and sign extend it to a 32-bits
            return (int)(short)(input.read() & 0xFFFF);
        }
    }

    private static class ShiftLeft extends Bus<Integer, Integer> {
        @Override
        public Integer read() {
            return input.read() << 2;
        }
    }

    private static class ShamtBus extends Bus<Integer, Byte> {
        @Override
        public Byte read() {
            return (byte)((input.read() >>> 6) & 0x1F);
        }
    }
}
