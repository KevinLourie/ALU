/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /** Data is located here */
    private DataMemory dataMemory;

    private Multiplexer<Integer> wbMux;

    /** D0 latch which connects to the data of the data memory */
    private Register<Integer> tLatch;

    /** D1 latch which is either the address of the data memory or the write back data. The type of instruction
     * determines how it's used */
    private Register<Integer> resultLatch;

    /** Memory write enable latch which decides whether the data in D0 is written to memory */
    private Register<Byte> memoryWriteEnableLatch;

    /**
     * Constructor
     * @param arr integer array
     */
    MemoryAccess(int[] arr, Cycler cycler) {
        wbMux = new Multiplexer<>();
        dataMemory = new DataMemory(cycler, arr);
        tLatch = new Register<>("MemoryAccess.T", 0, cycler);
        resultLatch = new Register<>("MemoryAccess.Result", 0, cycler);
        memoryWriteEnableLatch = new Register<>("MemoryAccess.MemoryWriteEnable", (byte)1, cycler);

        dataMemory
                .setDataInput(tLatch.getOutput())
                .setDataAddressInput(resultLatch.getOutput())
                .setEnableInput(memoryWriteEnableLatch.getOutput());
        wbMux.setInputs(dataMemory.getDataOutput(), resultLatch.getOutput());
    }


    public MemoryAccess setWbIndexInput(Output<Integer> wbMuxSelector) {
        wbMux.setIndexInput(wbMuxSelector);
        return this;
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Memory Access
     */
    public MemoryAccess setTInput(Output<Integer> tInput) {
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize resultLatch
     * @param resultInput input to resultLatch
     * @return Memory Access
     */
    public MemoryAccess setResultInput(Output<Integer> resultInput) {
        resultLatch.setInput(resultInput);
        return this;
    }

    public MemoryAccess setMemoryWriteEnableInput(Output<Byte> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Integer> getWb0Output() {
        return dataMemory.getDataOutput();
    }

    public Output<Integer> getWb1Output() {
        return resultLatch.getOutput();
    }

    public Output<Integer> getWbOutput() {
        return wbMux.getOutput();
    }
}
