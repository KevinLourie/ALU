/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    /**
     * Fetch instruction from here
     */
    private Memory memory;

    /**
     * Adds to address
     */
    private Adder adder;

    /**
     * Program counter
     */
    private Register<Integer> pc;

    /**
     * Where to get next program from
     */
    private Multiplexer<Integer> pcMux;

    /**
     * Constructor
     * @param memory main memory
     */
    InstructionFetch(Memory memory, Cycler cycler) {
        this.memory = memory;
        adder = new Adder();
        pc = new Register<>("pc", 0, cycler);
        pcMux = new Multiplexer<>();
    }

    /**
     * Initialize data register and address register
     * @param dataInput data register input
     * @param nextPCInput address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPCInput) {
        // TODO: add correct enable inputs
        memory.initData(dataInput, pc.getOutput(), null);
        adder.init(nextPCInput, new ConstantOutput<Integer>(4));
        pc.init(nextPCInput, null);
        pcMux.init(nextPCInput, null);
    }

    public Output<Integer> getInstructionOutput() {
        return memory.getInstructionOutput();
    }
}
