/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /**
     * Determine if branch condition is true by comparing data in S and T
     */
    Output<Number8> jumpEnable;

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
    private Register<Number32> instructionRegister;

    /**
     * Location of next instruction
     */
    private Register<Number32> nextPcLatch;

    private Multiplexer<Number32> sSelectorMux;

    private Multiplexer<Number32> tSelectorMux;

    /**
     * Constructor
     *
     * @param cycler cycler
     */
    InstructionDecode(Cycler cycler, int[] registers) {
        adder = new Adder();
        sSelectorMux = new Multiplexer<>(3);
        tSelectorMux = new Multiplexer<>(3);
        registerBank = new RegisterBank(cycler, registers);
        decoder = new Decoder();
        instructionRegister = new Register<>("InstructionDecode.instruction", Number32.zero, cycler);
        registerBank
                .setSSelectorInput(decoder.getSSelectorOutput())
                .setTSelectorInput(decoder.getTSelectorOutput());
        nextPcLatch = new Register<>("InstructionDecode.nextPcLatch", Number32.zero, cycler);

        // Determine if jump is enabled
        jumpEnable = () -> {
            // Check if jump is enabled
            Number8 branchCondition = decoder.getBranchConditionOutput().read();
            Number32 s = getSOutput().read();
            Number32 t = getTOutput().read();
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
            return new Number8(branch, src);
        };

        // Internal wiring
        sSelectorMux.setInput(0, registerBank.getSOutput());
        tSelectorMux.setInput(0, registerBank.getTOutput());

        adder
                .setInput1(nextPcLatch.getOutput())
                .setInput2(decoder.getImmediateOutput());

        decoder.setInstructionInput(instructionRegister.getOutput());
    }

    /**
     * Initialize instruction register
     *
     * @param instructionInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setInstructionInput(Output<Number32> instructionInput) {
        instructionRegister.setInput(instructionInput);
        return this;
    }

    public InstructionDecode setResultInput(Output<Number32> input) {
        sSelectorMux.setInput(1, input);
        tSelectorMux.setInput(1, input);
        return this;
    }

    public InstructionDecode setSMuxIndex(Output<Number8> sMuxIndex) {
        sSelectorMux.setIndexInput(sMuxIndex);
        return this;
    }

    public InstructionDecode setTMuxIndex(Output<Number8> tMuxIndex) {
        tSelectorMux.setIndexInput(tMuxIndex);
        return this;
    }

    /**
     * Initialize program counter
     *
     * @param nextPCInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setNextPcInput(Output<Number32> nextPCInput) {
        nextPcLatch.setInput(nextPCInput);
        return this;
    }

    public Output<Number32> getSOutput() {
        return sSelectorMux.getOutput();
    }

    public Output<Number32> getTOutput() {
        return tSelectorMux.getOutput();
    }

    public Output<Number8> getSSelectorOutput() {
        return decoder.getSSelectorOutput();
    }

    public Output<Number8> getTSelectorOutput() {
        return decoder.getTSelectorOutput();
    }

    public Output<Number32> getImmediateOutput() {
        return decoder.getImmediateOutput();
    }

    public Output<Number8> getAluMuxIndexOutput() {
        return decoder.getAluMuxIndexOutput();
    }

    public Output<Number8> getAluOpOutput() {
        return decoder.getAluOpOutput();
    }

    public Output<Number8> getMemoryWriteEnableOutput() {
        return decoder.getMemoryWriteEnableOutput();
    }

    public Output<Number8> getWbMuxIndexOutput() {
        return decoder.getWbMuxIndexOutput();
    }

    public Output<Number8> getWbSelectorMuxOutput() {
        return decoder.getWbSelectorOutput();
    }

    public Output<Number8> getWbEnableOutput() {
        return decoder.getWbEnableOutput();
    }

    public Output<Number8> getHaltOutput() {
        return decoder.getHalt();
    }

    /**
     * Setter for D input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbInput what to set D input to
     * @return Instruction Decode
     */
    public InstructionDecode setWbInput(Output<Number32> wbInput) {
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
    public InstructionDecode setWBSelectorInput(Output<Number8> wbSelectorInput) {
        registerBank.setWbSelectorInput(wbSelectorInput);
        return this;
    }

    /**
     * Setter for go
     * @param goInput what to set go to
     * @return Instruction Decode
     */
    public InstructionDecode setGoInput(Output<Number8> goInput) {
        decoder.setGo(goInput);
        return this;
    }

    /**
     * Getter for the next program counter. This is an input to the InstructionFetch. It is used for jumps
     *
     * @return next program counter
     */
    public Output<Number32> getNextPcOutput() {
        return adder.getOutput();
    }

    /**
     * Getter for jump enable. It will return 0 if the branch will be done or 1 if it is
     *
     * @return
     */
    public Output<Number8> getJumpEnable() {
        return jumpEnable;
    }

    /**
     * Setter for WB enable input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbEnableInput what to set WB enable input to
     * @return Instruction Decode
     */
    public InstructionDecode setWBEnableInput(Output<Number8> wbEnableInput) {
        registerBank.setWbEnableInput(wbEnableInput);
        return this;
    }
}
