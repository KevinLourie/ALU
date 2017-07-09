/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /** Data is located here */
    private DataMemory dataMemory;

    private Multiplexer<Number32> wbMux;

    /** D0 latch which connects to the data of the data memory */
    private Register<Number32> tLatch;

    /** D1 latch which is either the address of the data memory or the write back data. The type of instruction
     * determines how it's used */
    private Register<Number32> resultLatch;

    /** Memory write enable latch which decides whether the data in D0 is written to memory */
    private Register<Number8> memoryWriteEnableLatch;

    /**
     * Constructor
     * @param arr integer array
     */
    MemoryAccess(int[] arr, Cycler cycler) {
        wbMux = new Multiplexer<>(2);
        dataMemory = new DataMemory(cycler, arr);
        tLatch = new Register<>("MemoryAccess.T", Number32.zero, cycler);
        resultLatch = new Register<>("MemoryAccess.Result", Number32.zero, cycler);
        memoryWriteEnableLatch = new Register<>("MemoryAccess.MemoryWriteEnable", Number8.zero, cycler);

        dataMemory
                .setDataInput(tLatch.getOutput())
                .setDataAddressInput(resultLatch.getOutput())
                .setEnableInput(memoryWriteEnableLatch.getOutput());
        wbMux
                .setInput(0, dataMemory.getDataOutput())
                .setInput(1, resultLatch.getOutput());
    }


    public MemoryAccess setWbIndexInput(Output<Number8> wbMuxSelector) {
        wbMux.setIndexInput(wbMuxSelector);
        return this;
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Memory Access
     */
    public MemoryAccess setTInput(Output<Number32> tInput) {
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize resultLatch
     * @param resultInput input to resultLatch
     * @return Memory Access
     */
    public MemoryAccess setResultInput(Output<Number32> resultInput) {
        resultLatch.setInput(resultInput);
        return this;
    }

    public MemoryAccess setMemoryWriteEnableInput(Output<Number8> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Number32> getWb0Output() {
        return dataMemory.getDataOutput();
    }

    public Output<Number32> getWb1Output() {
        return resultLatch.getOutput();
    }

    public Output<Number32> getWbOutput() {
        return wbMux.getOutput();
    }
}
