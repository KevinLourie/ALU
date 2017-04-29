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
    private Register<Integer> PC;

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
        adder = new Adder(4);
        PC = new Register<>("PC", 0, cycler);
        pcMux = new Multiplexer<>();
    }

    /**
     * Initialize data register and address register
     * @param dataInput data register input
     * @param nextPCInput address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPCInput) {
        // TODO: add correct enable inputs
        memory.init(dataInput, PC.getOutput(), null);
        adder.init(nextPCInput);
        PC.init(nextPCInput, null);
        pcMux.init(nextPCInput, null);
    }
}
