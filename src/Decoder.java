/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    /** Contains the microinstruction to be executed */
    private Output<Boolean> wbEnableOutput;

    /** Contains the S register number */
    private Output<Byte> sSelectorOutput;

    /** Contains the T register number*/
    private Output<Byte> tSelectorOutput;

    /** Contains the D register number */
    private Output<Byte> dSelectorOutput;

    /** Contains the constant */
    private Output<Integer> constantOutput;

    /** Index of ALU mux */
    private Output<Integer> aluMuxIndexOutput;

    /** Index of PC mux */
    private Output<Integer> pcMuxIndexOutput;

    /** Index of write back mux */
    private Output<Integer> wbMuxIndexOutput;

    /** True if data will be written to memory */
    private Output<Boolean> memoryWriteEnableOutput;

    /** List of opcode microinstructions */
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    /** List of funct microinstructions */
    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    /** Instruction input */
    private Output<Integer> instructionInput;

    /** Opcode in instruction */
    private Output<Byte> opcodeInput;

    /** Type of operation in instruction */
    private Output<Byte> functInput;

    /** ALU operation in instruction */
    private Output<Byte> aluOpOutput;

    /** Index of write back selector */
    private Output<Byte> wbSelectorOutput;

    /** Shift amount output */
    private Output<Byte> shamtOutput;

    /**
     * Generate outputs for each of the fields in the microinstruction
     */
    Decoder() {
        for(int i = 0; i < 64; i++) {
            opcodeMicroInstructions[i] = new MicroInstruction();
            functMicroInstructions[i] = new MicroInstruction();
        }
        opcodeMicroInstructions[0].setWbEnable(false).setMemoryWriteEnable(false);
        opcodeMicroInstructions[35].setPcMuxIndex(1).setAluMuxIndex(1).setWbMuxIndex(0).setMemoryWriteEnable(false).setWbEnable(true);
        wbEnableOutput = () -> getMicroInstruction().isWbEnable();
        aluMuxIndexOutput = () -> getMicroInstruction().getAluMuxIndex();
        pcMuxIndexOutput = () -> getMicroInstruction().getPcMuxIndex();
        wbMuxIndexOutput = () -> getMicroInstruction().getWbMuxIndex();
        memoryWriteEnableOutput = () -> getMicroInstruction().isMemoryWriteEnable();
        aluOpOutput = () -> getMicroInstruction().getAluOp();
        wbSelectorOutput = () -> opcodeInput.read() == 0 ? dSelectorOutput.read() : tSelectorOutput.read();
        sSelectorOutput = () -> (byte)((instructionInput.read() >>> 21) & 0x1F);
        tSelectorOutput = () -> (byte)((instructionInput.read() >>> 16) & 0x1F);
        dSelectorOutput = () -> (byte)((instructionInput.read() >>> 11) & 0x1F);
        constantOutput = () -> (instructionInput.read() & 0xFFFF);
        functInput = () -> (byte)(instructionInput.read() & 0x3F);
        opcodeInput = () -> (byte)(instructionInput.read() >>> 26);
        shamtOutput = () -> (byte)((instructionInput.read() >>> 6) & 0x1F);
    }

    /**
     * Setter for instruction input
     * @param instructionInput instruction input
     */
    public void setInstructionInput(Output<Integer> instructionInput) {
        this.instructionInput = instructionInput;
    }

    /**
     * Getter for s output
     * @return s output
     */
    public Output<Byte> getSSelectorOutput() {
        return sSelectorOutput;
    }

    /**
     * Getter for t output
     * @return t output
     */
    public Output<Byte> getTSelectorOutput() {
        return tSelectorOutput;
    }

    /**
     * Getter for constant output
     * @return constant output
     */
    public Output<Integer> getConstantOutput() {
        return constantOutput;
    }

    /**
     * Get microinstruction. If opcode is 0, it is a register instruction. Get microinstruction by using
     * funct as index to functMicroInstructions. Otherwise, use opcode as index to opcodeMicroInstructions.
     * @return microinstruction
     */
    public MicroInstruction getMicroInstruction() {
        if (opcodeInput.read() == 0) {
            return functMicroInstructions[functInput.read()];
        }
        return opcodeMicroInstructions[opcodeInput.read()];
    }

    /**
     * Getter for memory write enable
     * @return memory write enable
     */
    public Output<Boolean> getMemoryWriteEnableOutput() {
        return memoryWriteEnableOutput;
    }

    /**
     * Getter for ALU operation
     * @return ALU operation
     */
    public Output<Byte> getAluOpOutput() {
        return aluOpOutput;
    }

    public void init(Output<Byte> opcodeInput, Output<Byte> functInput) {
        this.opcodeInput = opcodeInput;
        this.functInput = functInput;
    }

    /**
     * Getter for write back enable
     * @return write back enable
     */
    public Output<Boolean> getWbEnableOutput() {
        return wbEnableOutput;
    }

    /**
     * Getter for ALU mux index
     * @return ALU mux index
     */
    public Output<Integer> getAluMuxIndexOutput() {
        return aluMuxIndexOutput;
    }

    /**
     * Getter for pc mux index output
     * @return pc mux index output
     */
    public Output<Integer> getPcMuxIndexOutput() {
        return pcMuxIndexOutput;
    }

    /**
     * Getter for wb mux index output
     * @return wb mux index output
     */
    public Output<Integer> getWbMuxIndexOutput() {
        return wbMuxIndexOutput;
    }

    /**
     * Getter for wb selector mux index
     * @return wb selector mux index
     */
    public Output<Byte> getWbSelectorOutput() {
        return wbSelectorOutput;
    }
}
