/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /** Data is located here */
    private DataMemory dataMemory;

    private Multiplexer<Value32> wbMux;

    /** D0 latch which connects to the data of the data memory */
    private Register<Value32> tLatch;

    /** D1 latch which is either the address of the data memory or the write back data. The type of instruction
     * determines how it's used */
    private Register<Value32> resultLatch;

    /** Memory write enable latch which decides whether the data in D0 is written to memory */
    private Register<Value8> memoryWriteEnableLatch;

    /**
     * Constructor
     * @param arr integer array
     */
    MemoryAccess(int[] arr, Cycler cycler) {
        wbMux = new Multiplexer<>(2);
        dataMemory = new DataMemory(cycler, arr);
        tLatch = new Register<>("MemoryAccess.T", Value32.zero, cycler);
        resultLatch = new Register<>("MemoryAccess.Result", Value32.zero, cycler);
        memoryWriteEnableLatch = new Register<>("MemoryAccess.MemoryWriteEnable", Value8.zero, cycler);

        dataMemory
                .setDataInput(tLatch.getOutput())
                .setDataAddressInput(resultLatch.getOutput())
                .setEnableInput(memoryWriteEnableLatch.getOutput());
        wbMux
                .setInput(0, dataMemory.getDataOutput())
                .setInput(1, resultLatch.getOutput());
    }


    public MemoryAccess setWbIndexInput(Output<Value8> wbMuxSelector) {
        wbMux.setIndexInput(wbMuxSelector);
        return this;
    }

    public String toStringDelta() {
        Joiner j = new Joiner(" ", "MemoryAccess(", ")");
        j.add(resultLatch.toStringDelta());
        j.add(tLatch.toStringDelta());
        j.add(memoryWriteEnableLatch.toStringDelta());
        return j.toString();
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Memory Access
     */
    public MemoryAccess setTInput(Output<Value32> tInput) {
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize resultLatch
     * @param resultInput input to resultLatch
     * @return Memory Access
     */
    public MemoryAccess setResultInput(Output<Value32> resultInput) {
        resultLatch.setInput(resultInput);
        return this;
    }

    public MemoryAccess setMemoryWriteEnableInput(Output<Value8> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Value32> getWb0Output() {
        return dataMemory.getDataOutput();
    }

    public Output<Value32> getWb1Output() {
        return resultLatch.getOutput();
    }

    public Output<Value32> getWbOutput() {
        return wbMux.getOutput();
    }
}
