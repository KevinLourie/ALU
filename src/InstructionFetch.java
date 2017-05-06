/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    /** Fetch instruction from here */
    private Memory memory;

    /** Adds to address */
    private Adder adder;

    /** Program counter */
    private Register<Integer> pc;

    /** Where to get next program from */
    private Multiplexer<Integer> pcMux;

    /**
     * Constructor
     * @param memory main memory
     */
    InstructionFetch(Memory memory, Cycler cycler) {
        this.memory = memory;
        adder = new Adder();
        pc = new Register<>("InstructionFetch.pc", 0, cycler);
        pcMux = new Multiplexer<>();

        // Internal wiring
        adder.init(pc.getOutput(), new ConstantOutput<Integer>(4));
        pc.setInput(pcMux.getOutput());

    }

    /**
     * Initialize adder, PC, and pcMux
     * @param nextPCInput input to pcMux
     * @return Instruction Fetch
     */
    public InstructionFetch setNextPCInput(Output<Integer> nextPCInput) {
        pcMux.setInputs(nextPCInput, adder.getOutput());
        return this;
    }

    /**
     * Setter for pcMux index
     * @param pcMuxIndexInput index
     * @return Instruction Fetch
     */
    public InstructionFetch setPcMuxIndexInput(Output<Integer> pcMuxIndexInput) {
        pcMux.setIndexInput(pcMuxIndexInput);
        return this;
    }

    public Output<Integer> getInstructionOutput() {
        return memory.getInstructionOutput();
    }
}
