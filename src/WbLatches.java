/**
 * Created by kzlou on 5/7/2017.
 */
public class WbLatches {

    /** Latch for WB Selector */
    Register<Byte> wbSelectorLatch;

    /** Latch for WB enable */
    Register<Boolean> wbEnableLatch;

    /** Latch for WB mux index */
    Register<Integer> wbMuxIndexLatch;

    WbLatches(Cycler cycler) {
        wbSelectorLatch = new Register<>("WB Selector", (byte)0, cycler);
        wbEnableLatch = new Register<>("WB Enable", true, cycler);
        wbMuxIndexLatch = new Register<>("WB Mux Index", 0, cycler);
    }

    /**
     * Setter for WB enable latch input
     * @param wbEnableInput wb enable latch input
     * @return WbLatches
     */
    public WbLatches setWbEnableInput(Output<Boolean> wbEnableInput) {
        wbEnableLatch.setInput(wbEnableInput);
        return this;
    }

    /**
     * Setter for WB selector latch input
     * @param wbSelectorInput wb selector latch input
     * @return WbLatches
     */
    public WbLatches setWbSelectorInput(Output<Byte> wbSelectorInput) {
        wbSelectorLatch.setInput(wbSelectorInput);
        return this;
    }

    /**
     * Setter for WB mux index latch input
     * @param wbMuxIndexInput wb mux index latch input
     * @return WbLatches
     */
    public WbLatches setWbMuxIndexInput(Output<Integer> wbMuxIndexInput) {
        wbMuxIndexLatch.setInput(wbMuxIndexInput);
        return this;
    }

    /**
     * Getter for wb mux index output
     * @return wb mux index output
     */
    public Output<Integer> getWbMuxIndexOutput() {
        return wbMuxIndexLatch.getOutput();
    }

    /**
     * Getter for wb selector output
     * @return wb selector output
     */
    public Output<Byte> getWbSelectorOutput() {
        return wbSelectorLatch.getOutput();
    }

    /**
     * Getter for wb enable output
     * @return wb enable output
     */
    public Output<Boolean> getWbEnableOutput() {
        return wbEnableLatch.getOutput();
    }

    /**
     * Setter for latch inputs
     * @param wbLatches latch inputs
     */
    public void setLatchInputs(WbLatches wbLatches) {
        setWbEnableInput(wbLatches.getWbEnableOutput());
        setWbMuxIndexInput(wbLatches.getWbMuxIndexOutput());
        setWbSelectorInput(wbLatches.getWbSelectorOutput());
    }

}
