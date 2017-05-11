/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /** Data is located here */
    private DataMemory dataMemory;

    /** D0 latch which connects to the data of the data memory */
    private Register<Integer> d0Latch;

    /** D1 latch which is either the address of the data memory or the write back data. The type of instruction
     * determines how it's used */
    private Register<Integer> d1Latch;

    /** Memory write enable latch which decides whether the data in D0 is written to memory */
    private Register<Boolean> memoryWriteEnableLatch;

    /**
     * Constructor
     * @param arr integer array
     */
    MemoryAccess(Integer[] arr, Cycler cycler) {
        dataMemory = new DataMemory(cycler, arr);
        d0Latch = new Register<>("MemoryAcess.d0", 0, cycler);
        d1Latch = new Register<>("MemoryAcess.d1", 0, cycler);
        memoryWriteEnableLatch = new Register<>("MemoryAccess.MemoryWriteEnable", true, cycler);

        dataMemory
                .setDataInput(d0Latch.getOutput())
                .setDataAddressInput(d1Latch.getOutput())
                .setEnableInput(memoryWriteEnableLatch.getOutput());
    }

    /**
     * Initialize d0Latch
     * @param d0Input input to d0Latch
     * @return Memory Access
     */
    public MemoryAccess setD0Input(Output<Integer> d0Input) {
        d0Latch.setInput(d0Input);
        return this;
    }

    /**
     * Initialize d1Latch
     * @param d1Input input to d1Latch
     * @return Memory Access
     */
    public MemoryAccess setD1Input(Output<Integer> d1Input) {
        d1Latch.setInput(d1Input);
        return this;
    }

    public MemoryAccess setMemoryWriteEnableInput(Output<Boolean> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    /**
     * Initialize wbSelectorLatch
     * @param dAddressInput input to wbSelectorLatch
     * @return Memory Access
     */
    public MemoryAccess setdAddressInput(Output<Integer> dAddressInput) {
        d1Latch.setInput(dAddressInput);
        return this;
    }

    public Output<Integer> getWb0Output() {
        return dataMemory.getDataOutput();
    }

    public Output<Integer> getWb1Output() {
        return d1Latch.getOutput();
    }
}
