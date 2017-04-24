/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    /**
     * Fetch instruction from here
     */
    Memory memory;

    /**
     * Adds to address
     */
    Adder adder;

    /**
     * Program counter
     */
    Register<Integer> PC;

    /**
     * Where to get next program from
     */
    Multiplexer<Integer> pcMux;

    /**
     * Constructor
     * @param memory main memory
     */
    InstructionFetch(Memory memory) {
        this.memory = memory;
        adder = new Adder(4);
        PC = new Register<>("PC", 0);
        pcMux = new Multiplexer<>();
    }

    /**
     * Initialize data register and address register
     * @param dataInput data register input
     * @param nextPC address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPC) {
        memory.init(dataInput, PC.getOutput());
        adder.init(nextPC);
        PC.init(nextPC);
        pcMux.init(nextPC);
    }

    /**
     * Cycle memory and PC
     */
    public void cycle() {
        memory.cycle();
        PC.cycle();
    }
}
