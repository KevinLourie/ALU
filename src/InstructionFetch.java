/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    /** Fetch instruction from here */
    private InstructionMemory instructionMemory;

    /** Adds to address */
    private Adder adder;

    /** Program counter */
    private Register<Number32> pc;

    /** Where to get next program from */
    private Multiplexer<Number32> pcMux;

    /** Controls enable input for PC */
    Output<Number8> go;

    /**
     * Constructor
     * @param arr integer array
     */
    InstructionFetch(int[] arr, Cycler cycler) {
        instructionMemory = new InstructionMemory(arr);
        adder = new Adder();
        pc = new Register<>("InstructionFetch.pc", Number32.zero, cycler);
        pcMux = new Multiplexer<>(2);

        // Internal wiring
        adder.init(pc.getOutput(), new ConstantOutput<Number32>(new Number32(4, "constant")));
        pc.setInput(pcMux.getOutput());
        instructionMemory.setAddressInput(pc.getOutput());
        pcMux.setInput(0, adder.getOutput());
    }

    /**
     * Initialize adder, PC, and pcMux
     * @param jumpAddressInput input to pcMux
     * @return Instruction Fetch
     */
    public InstructionFetch setJumpAddressInput(Output<Number32> jumpAddressInput) {
        pcMux.setInput(1, jumpAddressInput);
        return this;
    }

    /**
     * Setter for pcMux index
     * @param jumpEnableInput 1 to enable jump
     * @return Instruction Fetch
     */
    public InstructionFetch setJumpEnableInput(Output<Number8> jumpEnableInput) {
        pcMux.setIndexInput(jumpEnableInput);
        return this;
    }

    /**
     * Setter for enable input for PC
     * @return InstructionFetch
     */
    public InstructionFetch setPcEnable() {
        pc.setEnableInput(go);
        return this;
    }

    /**
     * Getter for instruction
     * @return instruction
     */
    public Output<Number32> getInstructionOutput() {
        return instructionMemory.getInstructionOutput();
    }

    /**
     * Getter for the next PC. It is an input to the Instruction Decode for computing the branch target
     * @return next PC
     */
    public Output<Number32> getNextPcOutput() {
        return pcMux.getOutput();
    }
}
