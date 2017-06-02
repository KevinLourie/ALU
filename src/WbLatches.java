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

    Register<Boolean> haltEnableLatch;

    WbLatches(Cycler cycler, String name) {
        wbSelectorLatch = new Register<>(name + ".wbSelector", (byte)0, cycler);
        wbEnableLatch = new Register<>(name + ".wbEnable", false, cycler);
        wbMuxIndexLatch = new Register<>(name + ".wbMuxIndex", 0, cycler);
        haltEnableLatch = new Register<>(name + ".haltEnable", false, cycler);
    }

    public Output<Boolean> getHaltEnableLatch() {
        return haltEnableLatch.getOutput();
    }

    public void setHaltEnableLatch(Output<Boolean> haltEnableInput) {
        haltEnableLatch.setInput(haltEnableInput);
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
        setHaltEnableLatch(wbLatches.getHaltEnableLatch());
    }

}
