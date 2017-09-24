/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /**
     * Determine if branch condition is true by comparing data in S and T
     */
    Output<Value8> jumpEnable;

    /**
     * Register bank
     */
    private RegisterBank registerBank;
    private Adder adder;
    /**
     * Returns microcode for instruction
     */
    private Decoder decoder;
    /**
     * Holds instruction
     */
    private Register<Value32> instructionRegister;

    /**
     * Location of next instruction
     */
    private Register<Value32> nextPcLatch;

    private Multiplexer<Value32> sSelectorMux;

    private Multiplexer<Value32> tSelectorMux;

    /** For turning off wbEnable and memoryWriteEnable if go is false */
    Gate wbEnableGate;

    /** For turning off memoryWriteEnable if go is false */
    Gate memoryWriteEnableGate;

    /** For controlling whether a branch is done or not */
    Gate jumpEnableGate;

    /**
     * Constructor
     *
     * @param cycler cycler
     */
    InstructionDecode(Cycler cycler, int[] registers) {
        wbEnableGate = new Gate(Gate.and2);
        memoryWriteEnableGate = new Gate(Gate.and2);
        jumpEnableGate = new Gate(Gate.and2);
        // If go is false, then turn off WB enable
        adder = new Adder();
        sSelectorMux = new Multiplexer<>(3);
        tSelectorMux = new Multiplexer<>(3);
        registerBank = new RegisterBank(cycler, registers);
        decoder = new Decoder();
        instructionRegister = new Register<>("InstructionDecode.instruction", Value32.zero, cycler);
        registerBank
                .setSSelectorInput(decoder.getSSelectorOutput())
                .setTSelectorInput(decoder.getTSelectorOutput());
        nextPcLatch = new Register<>("InstructionDecode.nextPcLatch", Value32.zero, cycler);

        // Determine if jump is enabled
        jumpEnable = () -> {
            // Check if jump is enabled
            Value8 branchCondition = decoder.getBranchConditionOutput().read();
            Value32 s = getSOutput().read();
            Value32 t = getTOutput().read();
            String srcFixed = String.format("JumpEnable(%s)", branchCondition);
            String src = String.format("JumpEnable(%s, %s, %s)", branchCondition, s, t);
            byte branch = 0;
            switch (branchCondition.byteValue()) {
                case BranchCondition.never:
                    src = srcFixed;
                    break;
                case BranchCondition.always:
                    src = srcFixed;
                    branch = 1;
                    break;
                case BranchCondition.equal:
                    if (s.intValue() == t.intValue()) {
                        branch = 1;
                    }
                    break;
                case BranchCondition.notEqual:
                    if (s.intValue() != t.intValue()) {
                        branch = 1;
                    }
                    break;
                case BranchCondition.greaterThan:
                    if (s.intValue() > t.intValue()) {
                        branch = 1;
                    }
                    break;
                case BranchCondition.greaterThanOrEqualTo:
                    if (s.intValue() >= t.intValue()) {
                        branch = 1;
                    }
                    break;
                default:
                    throw new RuntimeException("Bad branch condition");
            }
            return new Value8(branch, src);
        };

        // Internal wiring
        wbEnableGate.setInput(1, decoder.getWbEnableOutput());
        memoryWriteEnableGate.setInput(1, decoder.getMemoryWriteEnableOutput());
        jumpEnableGate.setInput(1, jumpEnable);

        sSelectorMux.setInput(0, registerBank.getSOutput());
        tSelectorMux.setInput(0, registerBank.getTOutput());

        adder.setInput1(nextPcLatch.getOutput())
                .setInput2(decoder.getImmediateOutput());

        decoder.setInstructionInput(instructionRegister.getOutput());
    }

    /**
     * Initialize instruction register
     *
     * @param instructionInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setInstructionInput(Output<Value32> instructionInput) {
        instructionRegister.setInput(instructionInput);
        return this;
    }

    public InstructionDecode setResultInput(Output<Value32> input) {
        sSelectorMux.setInput(1, input);
        tSelectorMux.setInput(1, input);
        return this;
    }

    public InstructionDecode setSMuxIndex(Output<Value8> sMuxIndex) {
        sSelectorMux.setIndexInput(sMuxIndex);
        return this;
    }

    public InstructionDecode setTMuxIndex(Output<Value8> tMuxIndex) {
        tSelectorMux.setIndexInput(tMuxIndex);
        return this;
    }

    public void setGo(Output<Value8> goInput) {
        wbEnableGate.setInput(0, goInput);
        memoryWriteEnableGate.setInput(0, goInput);
        jumpEnableGate.setInput(0, goInput);
    }

    /**
     * Initialize program counter
     *
     * @param nextPCInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setNextPcInput(Output<Value32> nextPCInput) {
        nextPcLatch.setInput(nextPCInput);
        return this;
    }

    public Output<Value32> getSOutput() {
        return sSelectorMux.getOutput();
    }

    public Output<Value32> getTOutput() {
        return tSelectorMux.getOutput();
    }

    public Output<Value8> getSSelectorOutput() {
        return decoder.getSSelectorOutput();
    }

    public Output<Value8> getTSelectorOutput() {
        return decoder.getTSelectorOutput();
    }

    public Output<Value32> getImmediateOutput() {
        return decoder.getImmediateOutput();
    }

    public Output<Value8> getAluMuxIndexOutput() {
        return decoder.getAluMuxIndexOutput();
    }

    public Output<Value8> getAluOpOutput() {
        return decoder.getAluOpOutput();
    }

    public Output<Value8> getMemoryWriteEnableOutput() {
        return memoryWriteEnableGate.getOutput();
    }

    public Output<Value8> getWbMuxIndexOutput() {
        return decoder.getWbMuxIndexOutput();
    }

    public Output<Value8> getWbSelectorMuxOutput() {
        return decoder.getWbSelectorOutput();
    }

    public Output<Value8> getWbEnableOutput() {
        return wbEnableGate.getOutput();
    }

    public Output<Value8> getHaltOutput() {
        return decoder.getHalt();
    }

    /**
     * Setter for D input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbInput what to set D input to
     * @return Instruction Decode
     */
    public InstructionDecode setWbInput(Output<Value32> wbInput) {
        registerBank.setWbInput(wbInput);
        sSelectorMux.setInput(2, wbInput);
        tSelectorMux.setInput(2, wbInput);
        return this;
    }

    /**
     * Setter for WB selector input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbSelectorInput what to set WB selector input to
     * @return Instruction Decode
     */
    public InstructionDecode setWBSelectorInput(Output<Value8> wbSelectorInput) {
        registerBank.setWbSelectorInput(wbSelectorInput);
        return this;
    }

    /**
     * Getter for the next program counter. This is an input to the InstructionFetch. It is used for jumps
     *
     * @return next program counter
     */
    public Output<Value32> getNextPcOutput() {
        return adder.getOutput();
    }

    /**
     * Getter for jump enable. It will return 0 if the branch will be done or 1 if it is
     *
     * @return
     */
    public Output<Value8> getJumpEnable() {
        return jumpEnableGate.getOutput();
    }

    /**
     * Setter for WB enable input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbEnableInput what to set WB enable input to
     * @return Instruction Decode
     */
    public InstructionDecode setWBEnableInput(Output<Value8> wbEnableInput) {
        registerBank.setWbEnableInput(wbEnableInput);
        return this;
    }
}
