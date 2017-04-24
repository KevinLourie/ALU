/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /**
     * Data is located here
     */
    Memory memory;

    /**
     * Data register
     */
    Register<Integer> dataRegister;

    /**
     * Address register
     */
    Register<Integer> addressRegister;

    /**
     * Constructor
     * @param memory main memory
     */
    MemoryAccess(Memory memory) {
        this.memory = memory;
    }

    /**
     * Initialize the data register and address register
     * @param dataInput data register input
     * @param nextPC address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPC) {
        dataRegister.init(dataInput);
        addressRegister.init(nextPC);
        memory.init(dataRegister.getOutput(), addressRegister.getOutput());
    }

    /**
     * Cycle the memory and registers
     */
    public void cycle() {
        memory.cycle();
        dataRegister.cycle();
        addressRegister.cycle();
    }

}
