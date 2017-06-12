/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    /**
     * Determine if branch condition is true by comparing data in S and T
     */
    Output<Integer> jumpEnable;

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
    private Register<Integer> instructionRegister;

    /**
     * Location of next instruction
     */
    private Register<Integer> nextPcLatch;

    /**
     * Constructor
     *
     * @param cycler cycler
     */
    InstructionDecode(Cycler cycler, int[] registers) {
        adder = new Adder();
        registerBank = new RegisterBank(cycler, registers);
        decoder = new Decoder();
        instructionRegister = new Register<>("InstructionDecode.instruction", 0, cycler);
        registerBank
                .setSSelectorInput(decoder.getSSelectorOutput())
                .setTSelectorInput(decoder.getTSelectorOutput());
        nextPcLatch = new Register<>("InstructionDecode.nextPcLatch", 0, cycler);

        // Determine if jump is enabled
        jumpEnable = () -> {
            // Check if jump is enabled
            switch (decoder.getBranchConditionOutput().read()) {
                case BranchCondition.never:
                    return 0;
                case BranchCondition.always:
                    return 1;
                case BranchCondition.equal:
                    if (registerBank.getSOutput().read() == registerBank.getTOutput().read()) {
                        return 1;
                    }
                    return 0;
                case BranchCondition.notEqual:
                    if (registerBank.getSOutput().read() != registerBank.getTOutput().read()) {
                        return 1;
                    }
                    return 0;
                case BranchCondition.greaterThan:
                    if (registerBank.getSOutput().read() > registerBank.getTOutput().read()) {
                        return 1;
                    }
                    return 0;
                case BranchCondition.greaterThanOrEqualTo:
                    if (registerBank.getSOutput().read() >= registerBank.getTOutput().read()) {
                        return 1;
                    }
                    return 0;
            }
            return 0;
        };

        // Internal wiring
        adder
                .setInput1(nextPcLatch.getOutput())
                .setInput2(decoder.getConstantOutput());
        decoder.setInstructionInput(instructionRegister.getOutput());
    }

    /**
     * Initialize instruction register
     *
     * @param instructionInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setInstructionInput(Output<Integer> instructionInput) {
        instructionRegister.setInput(instructionInput);
        return this;
    }

    /**
     * Initialize program counter
     *
     * @param nextPCInput input to instruction register
     * @return Instruction Decode
     */
    public InstructionDecode setNextPcInput(Output<Integer> nextPCInput) {
        nextPcLatch.setInput(nextPCInput);
        return this;
    }

    public Output<Integer> getSOutput() {
        return registerBank.getSOutput();
    }

    public Output<Integer> getTOutput() {
        return registerBank.getTOutput();
    }

    public Output<Byte> getSSelectorOutput() {
        return decoder.getSSelectorOutput();
    }

    public Output<Byte> getTSelectorOutput() {
        return decoder.getTSelectorOutput();
    }

    public Output<Integer> getCOutput() {
        return decoder.getConstantOutput();
    }

    public Output<Integer> getAluMuxIndexOutput() {
        return decoder.getAluMuxIndexOutput();
    }

    public Output<Byte> getAluOpOutput() {
        return decoder.getAluOpOutput();
    }

    public Output<Byte> getMemoryWriteEnableOutput() {
        return decoder.getMemoryWriteEnableOutput();
    }

    public Output<Integer> getWbMuxIndexOutput() {
        return decoder.getWbMuxIndexOutput();
    }

    public Output<Byte> getWbSelectorMuxOutput() {
        return decoder.getWbSelectorOutput();
    }

    public Output<Byte> getWbEnableOutput() {
        return decoder.getWbEnableOutput();
    }

    public Output<Byte> getHaltOutput() {
        return decoder.getHalt();
    }

    /**
     * Setter for D input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbInput what to set D input to
     * @return Instruction Decode
     */
    public InstructionDecode setWbInput(Output<Integer> wbInput) {
        registerBank.setWbInput(wbInput);
        return this;
    }

    /**
     * Setter for WB selector input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbSelectorInput what to set WB selector input to
     * @return Instruction Decode
     */
    public InstructionDecode setWBSelectorInput(Output<Byte> wbSelectorInput) {
        registerBank.setWbSelectorInput(wbSelectorInput);
        return this;
    }

    /**
     * Getter for the next program counter. This is an input to the InstructionFetch. It is used for jumps
     *
     * @return next program counter
     */
    public Output<Integer> getNextPcOutput() {
        return adder.getOutput();
    }

    /**
     * Getter for jump enable. It will return 0 if the branch will be done or 1 if it is
     *
     * @return
     */
    public Output<Integer> getJumpEnable() {
        return jumpEnable;
    }

    /**
     * Setter for WB enable input in register bank. Comes from Write Back, so no latch is needed
     *
     * @param wbEnableInput what to set WB enable input to
     * @return Instruction Decode
     */
    public InstructionDecode setWBEnableInput(Output<Byte> wbEnableInput) {
        registerBank.setWbEnableInput(wbEnableInput);
        return this;
    }
}
