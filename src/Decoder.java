/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    /** Contains the microinstruction to be executed */
    private Output<Value8> wbEnableOutput;

    /** Contains the S register number */
    private Output<Value8> sSelectorOutput;

    /** Contains the T register number*/
    private Output<Value8> tSelectorOutput;

    /** Contains the D register number */
    private Output<Value8> dSelectorOutput;

    /** Contains the constant */
    private Output<Value32> immediateOutput;

    /** Index of ALU mux */
    private Output<Value8> aluMuxIndexOutput;

    /** Index of PC mux */
    private Output<Value8> branchConditionOutput;

    /** Index of write back mux */
    private Output<Value8> wbMuxIndexOutput;

    /** True if data will be written to memory */
    private Output<Value8> memoryWriteEnableOutput;

    /** List of value microinstructions */
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    /** List of funct microinstructions */
    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    /** Instruction input */
    private Output<Value32> instructionInput;

    /** Opcode in instruction */
    private Output<Value8> opcodeOutput;

    /** Type of operation in instruction */
    private Output<Value8> functOutput;

    /** ALU operation in instruction */
    private Output<Value8> aluOpOutput;

    /** Index of write back selector */
    private Output<Value8> wbSelectorOutput;

    /** Shift amount output */
    private Output<Value8> shamtOutput;

    public Output<Value8> getHalt() {
        return halt;
    }

    private Output<Value8> halt;

    /**
     * Generate outputs for each of the fields in the microinstruction. If go is false, instruction must be a no op
     */
    Decoder() {
        initMicrocode();

                // If go is false, then turn off WB enable
        wbEnableOutput = () -> new Value8(getMicroInstruction().isWbEnable());
        opcodeOutput = () -> new Value8(instructionInput.read().intValue() >>> 26);
        aluMuxIndexOutput = () -> new Value8(getMicroInstruction().getAluMuxIndex());
        // If go is false, do not branch
        branchConditionOutput = () -> new Value8(getMicroInstruction().getBranchCondition());
        wbMuxIndexOutput = () -> new Value8(getMicroInstruction().getWbMuxIndex());
        // If go is false, do not write to memory
        memoryWriteEnableOutput = () -> new Value8(getMicroInstruction().isMemoryWriteEnable());
        aluOpOutput = () -> new Value8(getMicroInstruction().getAluOp());
        wbSelectorOutput = () -> opcodeOutput.read().byteValue() == 0 ? dSelectorOutput.read() : tSelectorOutput.read();
        sSelectorOutput = () -> new Value8((instructionInput.read().intValue() >>> 21) & 0x1F);
        tSelectorOutput = () -> new Value8((instructionInput.read().intValue() >>> 16) & 0x1F);
        dSelectorOutput = () -> new Value8((instructionInput.read().intValue() >>> 11) & 0x1F);
        immediateOutput = () -> new Value32((short)(instructionInput.read().intValue() & 0xFFFF));
        functOutput = () -> new Value8(instructionInput.read().intValue() & 0x3F);
        shamtOutput = () -> new Value8((instructionInput.read().intValue() >>> 6) & 0x1F);
        // Don't need to stall halt because it cannot have a data hazard
        halt = () -> new Value8(getMicroInstruction().isWait());
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
    public void setInstructionInput(Output<Value32> instructionInput) {
        this.instructionInput = instructionInput;
    }

    /**
     * Getter for s output
     * @return s output
     */
    public Output<Value8> getSSelectorOutput() {
        return sSelectorOutput;
    }

    /**
     * Getter for t output
     * @return t output
     */
    public Output<Value8> getTSelectorOutput() {
        return tSelectorOutput;
    }

    /**
     * Getter for constant output
     * @return constant output
     */
    public Output<Value32> getImmediateOutput() {
        return immediateOutput;
    }

    /**
     * Get microinstruction. If value is 0, it is a register instruction. Get microinstruction by using
     * funct as index to functMicroInstructions. Otherwise, use value as index to opcodeMicroInstructions.
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
    public Output<Value8> getMemoryWriteEnableOutput() {
        return memoryWriteEnableOutput;
    }

    /**
     * Getter for ALU operation
     * @return ALU operation
     */
    public Output<Value8> getAluOpOutput() {
        return aluOpOutput;
    }

    public void init(Output<Value8> opcodeInput, Output<Value8> functInput) {
        this.opcodeOutput = opcodeInput;
        this.functOutput = functInput;
    }

    /**
     * Getter for write back enable
     * @return write back enable
     */
    public Output<Value8> getWbEnableOutput() {
        return wbEnableOutput;
    }

    /**
     * Getter for ALU mux index
     * @return ALU mux index
     */
    public Output<Value8> getAluMuxIndexOutput() {
        return aluMuxIndexOutput;
    }

    /**
     * Getter for pc mux index output
     * @return pc mux index output
     */
    public Output<Value8> getBranchConditionOutput() {
        return branchConditionOutput;
    }

    /**
     * Getter for wb mux index output
     * @return wb mux index output
     */
    public Output<Value8> getWbMuxIndexOutput() {
        return wbMuxIndexOutput;
    }

    /**
     * Getter for wb selector mux index
     * @return wb selector mux index
     */
    public Output<Value8> getWbSelectorOutput() {
        return wbSelectorOutput;
    }
}
