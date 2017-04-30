/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /**
     * Data is located here
     */
    private Memory memory;

    /**
     * Data latch
     */
    private Register<Integer> d0Latch;

    /**
     * ALU latch
     */
    private Register<Integer> d1Latch;

    /**
     * Indicates which register to write back to
     */
    private Register<Byte> wbSelectorLatch;

    /**
     * Indicates whether to write to a register
     */
    private Register<Boolean> wbEnableLatch;

    /**
     * Choooses between WB0 and WB1
     */
    private Register<Integer> wbMuxIndexLatch;

    /**
     * Constructor
     * @param memory main memory
     */
    MemoryAccess(Memory memory, Cycler cycler) {
        this.memory = memory;
    }

    /**
     * Initialize the data register and d1Input register
     * @param d0Input data register input
     * @param d0Input
     * @param d1Input
     * @param dAddressInput
     * @param enableInput
     * @param indexInput
     */
    public void init(Output<Integer> d0Input, Output<Integer> d1Input,
                     Output<Byte> dAddressInput, Output<Boolean> enableInput, Output<Integer> indexInput) {
        // TODO: add correct enable inputs
        wbSelectorLatch.init(dAddressInput);
        wbEnableLatch.init(enableInput);
        wbMuxIndexLatch.init(indexInput);
        d0Latch.init(d0Input);
        d1Latch.init(d1Input);
        memory.initData(d0Latch.getOutput(), d1Latch.getOutput(), null);
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
