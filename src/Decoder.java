/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    /** Contains the microinstruction to be executed */
    private Output<Byte> wbEnableOutput;

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
    private Output<Byte> branchConditionOutput;

    /** Index of write back mux */
    private Output<Integer> wbMuxIndexOutput;

    /** True if data will be written to memory */
    private Output<Byte> memoryWriteEnableOutput;

    /** List of opcode microinstructions */
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    /** List of funct microinstructions */
    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    /** Instruction input */
    private Output<Integer> instructionInput;

    /** Opcode in instruction */
    private Output<Byte> opcodeOutput;

    /** Type of operation in instruction */
    private Output<Byte> functOutput;

    /** ALU operation in instruction */
    private Output<Byte> aluOpOutput;

    /** Index of write back selector */
    private Output<Byte> wbSelectorOutput;

    /** Shift amount output */
    private Output<Byte> shamtOutput;

    public Output<Byte> getHalt() {
        return halt;
    }

    private Output<Byte> halt;

    Output<Byte> go;

    /**
     * Generate outputs for each of the fields in the microinstruction. If go is false, instruction must be a no op
     */
    Decoder() {
        initMicrocode();

                // If go is false, then turn off WB enable
        wbEnableOutput = () -> (byte)(getMicroInstruction().isWbEnable() & go.read());
        opcodeOutput = () -> (byte)(instructionInput.read() >>> 26);
        aluMuxIndexOutput = () -> getMicroInstruction().getAluMuxIndex();
        // If go is false, do not branch
        branchConditionOutput = () -> (byte)(getMicroInstruction().getBranchCondition() & go.read());
        wbMuxIndexOutput = () -> getMicroInstruction().getWbMuxIndex();
        // If go is false, do not write to memory
        memoryWriteEnableOutput = () -> (byte)(getMicroInstruction().isMemoryWriteEnable() & go.read());
        aluOpOutput = () -> getMicroInstruction().getAluOp();
        wbSelectorOutput = () -> opcodeOutput.read() == 0 ? dSelectorOutput.read() : tSelectorOutput.read();
        sSelectorOutput = () -> (byte)((instructionInput.read() >>> 21) & 0x1F);
        tSelectorOutput = () -> (byte)((instructionInput.read() >>> 16) & 0x1F);
        dSelectorOutput = () -> (byte)((instructionInput.read() >>> 11) & 0x1F);
        constantOutput = () -> (int)(short)(instructionInput.read() & 0xFFFF);
        functOutput = () -> (byte)(instructionInput.read() & 0x3F);
        shamtOutput = () -> (byte)((instructionInput.read() >>> 6) & 0x1F);
        // Don't need to stall halt because it cannot have a data hazard
        halt = () -> getMicroInstruction().isWait();
    }

    private void initMicrocode() {
        for(int i = 0; i < 64; i++) {
            opcodeMicroInstructions[i] = new MicroInstruction();
            functMicroInstructions[i] = new MicroInstruction();
        }

        // Load
        opcodeMicroInstructions[0x23]
                .setAluMuxIndex(1)
                .setWbMuxIndex(0)
                .setWbEnable((byte)1);

        // Store
        opcodeMicroInstructions[0x2B]
                .setAluMuxIndex(1)
                .setMemoryWriteEnable((byte)1);

        // Branch on equal
        opcodeMicroInstructions[0x04]
                .setBranchCondition(BranchCondition.equal);

        opcodeMicroInstructions[0x05]
                .setBranchCondition(BranchCondition.notEqual);

        opcodeMicroInstructions[0x08]
                .setHalt();

        // Add
        functMicroInstructions[0x20]
                .setAluOp(AluOp.Add)
                .setWbMuxIndex(1)
                .setWbEnable((byte)1);

        functMicroInstructions[0x22]
                .setAluOp(AluOp.Subtract)
                .setWbMuxIndex(1)
                .setWbEnable((byte)1);

        functMicroInstructions[0x18]
                .setAluOp(AluOp.Multiply)
                .setWbMuxIndex(1)
                .setWbEnable((byte)1);

    }

    /**
     * Setter for instruction input
     * @param instructionInput instruction input
     */
    public void setInstructionInput(Output<Integer> instructionInput) {
        this.instructionInput = instructionInput;
    }

    public void setGo(Output<Byte> go) {
        this.go = go;
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
        if (opcodeOutput.read() == 0) {
            return functMicroInstructions[functOutput.read()];
        }
        return opcodeMicroInstructions[opcodeOutput.read()];
    }

    /**
     * Getter for memory write enable
     * @return memory write enable
     */
    public Output<Byte> getMemoryWriteEnableOutput() {
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
        this.opcodeOutput = opcodeInput;
        this.functOutput = functInput;
    }

    /**
     * Getter for write back enable
     * @return write back enable
     */
    public Output<Byte> getWbEnableOutput() {
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
    public Output<Byte> getBranchConditionOutput() {
        return branchConditionOutput;
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
