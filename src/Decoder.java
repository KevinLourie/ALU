/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    /** Contains the microinstruction to be executed */
    private Output<Number8> wbEnableOutput;

    /** Contains the S register number */
    private Output<Number8> sSelectorOutput;

    /** Contains the T register number*/
    private Output<Number8> tSelectorOutput;

    /** Contains the D register number */
    private Output<Number8> dSelectorOutput;

    /** Contains the constant */
    private Output<Integer> constantOutput;

    /** Index of ALU mux */
    private Output<Integer> aluMuxIndexOutput;

    /** Index of PC mux */
    private Output<Number8> branchConditionOutput;

    /** Index of write back mux */
    private Output<Integer> wbMuxIndexOutput;

    /** True if data will be written to memory */
    private Output<Number8> memoryWriteEnableOutput;

    /** List of opcode microinstructions */
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    /** List of funct microinstructions */
    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    /** Instruction input */
    private Output<Integer> instructionInput;

    /** Opcode in instruction */
    private Output<Number8> opcodeOutput;

    /** Type of operation in instruction */
    private Output<Number8> functOutput;

    /** ALU operation in instruction */
    private Output<Number8> aluOpOutput;

    /** Index of write back selector */
    private Output<Number8> wbSelectorOutput;

    /** Shift amount output */
    private Output<Number8> shamtOutput;

    public Output<Number8> getHalt() {
        return halt;
    }

    private Output<Number8> halt;

    Output<Number8> go;

    /**
     * Generate outputs for each of the fields in the microinstruction. If go is false, instruction must be a no op
     */
    Decoder() {
        initMicrocode();

                // If go is false, then turn off WB enable
        wbEnableOutput = () -> new Number8(getMicroInstruction().isWbEnable() & go.read().byteValue(), "wbEnable");
        opcodeOutput = () -> new Number8(instructionInput.read() >>> 26, "opcode");
        aluMuxIndexOutput = () -> getMicroInstruction().getAluMuxIndex();
        // If go is false, do not branch
        branchConditionOutput = () -> new Number8(getMicroInstruction().getBranchCondition() & go.read().byteValue(),
                "branchCondition");
        wbMuxIndexOutput = () -> getMicroInstruction().getWbMuxIndex();
        // If go is false, do not write to memory
        memoryWriteEnableOutput = () -> new Number8(getMicroInstruction().isMemoryWriteEnable() & go.read().byteValue(), "memoryWriteEnable");
        aluOpOutput = () -> new Number8(getMicroInstruction().getAluOp(), "aluOp");
        wbSelectorOutput = () -> opcodeOutput.read().byteValue() == 0 ? dSelectorOutput.read() : tSelectorOutput.read();
        sSelectorOutput = () -> new Number8((instructionInput.read() >>> 21) & 0x1F, "sSelector");
        tSelectorOutput = () -> new Number8((instructionInput.read() >>> 16) & 0x1F, "tSelector");
        dSelectorOutput = () -> new Number8((instructionInput.read() >>> 11) & 0x1F, "dSelector");
        constantOutput = () -> (int)(short)(instructionInput.read() & 0xFFFF);
        functOutput = () -> new Number8(instructionInput.read() & 0x3F, "funct");
        shamtOutput = () -> new Number8((instructionInput.read() >>> 6) & 0x1F, "shamt");
        // Don't need to stall halt because it cannot have a data hazard
        halt = () -> new Number8(getMicroInstruction().isWait(), "halt");
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

    public void setGo(Output<Number8> go) {
        this.go = go;
    }

    /**
     * Getter for s output
     * @return s output
     */
    public Output<Number8> getSSelectorOutput() {
        return sSelectorOutput;
    }

    /**
     * Getter for t output
     * @return t output
     */
    public Output<Number8> getTSelectorOutput() {
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
        if (opcodeOutput.read().byteValue() == 0) {
            return functMicroInstructions[functOutput.read().intValue()];
        }
        return opcodeMicroInstructions[opcodeOutput.read().intValue()];
    }

    /**
     * Getter for memory write enable
     * @return memory write enable
     */
    public Output<Number8> getMemoryWriteEnableOutput() {
        return memoryWriteEnableOutput;
    }

    /**
     * Getter for ALU operation
     * @return ALU operation
     */
    public Output<Number8> getAluOpOutput() {
        return aluOpOutput;
    }

    public void init(Output<Number8> opcodeInput, Output<Number8> functInput) {
        this.opcodeOutput = opcodeInput;
        this.functOutput = functInput;
    }

    /**
     * Getter for write back enable
     * @return write back enable
     */
    public Output<Number8> getWbEnableOutput() {
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
    public Output<Number8> getBranchConditionOutput() {
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
    public Output<Number8> getWbSelectorOutput() {
        return wbSelectorOutput;
    }
}
