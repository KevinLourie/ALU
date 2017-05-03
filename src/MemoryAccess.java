/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /** Data is located here */
    private Memory memory;

    /** Data latch */
    private Register<Integer> d0Latch;

    /** ALU latch */
    private Register<Integer> d1Latch;

    /** Indicates which register to write back to */
    private Register<Byte> wbSelectorLatch;

    /** Indicates whether to write to a register */
    private Register<Boolean> wbEnableLatch;

    /** Choooses between WB0 and WB1 */
    private Register<Integer> wbMuxIndexLatch;

    /**
     * Constructor
     * @param memory main memory
     */
    MemoryAccess(Memory memory, Cycler cycler) {
        this.memory = memory;
        memory.initData(d0Latch.getOutput(), d1Latch.getOutput(), null);
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

    /**
     * Initialize wbSelectorLatch
     * @param dAddressInput input to wbSelectorLatch
     * @return Memory Access
     */
    public MemoryAccess setdAddressInput(Output<Byte> dAddressInput) {
        wbSelectorLatch.setInput(dAddressInput);
        return this;
    }

    /**
     * Initialize wbEnableLatch
     * @param enableInput input to wbEnableLatch
     * @return Memory Access
     */
    public MemoryAccess setEnableOutput(Output<Boolean> enableInput) {
        wbEnableLatch.setInput(enableInput);
        return this;
    }

    /**
     * Initialize wbMuxIndexLatch
     * @param indexInput input to wbMuxIndexLatch
     * @return Memory Access
     */
    public MemoryAccess setIndexInput(Output<Integer> indexInput) {
        wbMuxIndexLatch.setInput(indexInput);
        return this;
    }

    public Output<Integer> getWb0Output() {
        return memory.getDataOutput();
    }

    public Output<Integer> getWb1Output() {
        return d1Latch.getOutput();
    }

    public Output<Byte> getWbSelectorOutput() {
        return wbSelectorLatch.getOutput();
    }

    public Output<Boolean> getWbEnableOutput() {
        return wbEnableLatch.getOutput();
    }

    public Output<Integer> getDIndexOutput() {
        return wbMuxIndexLatch.getOutput();
    }
}
