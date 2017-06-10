/**
 * Created by kzlou on 5/7/2017.
 */
public class WbLatches {

    /** Latch for WB Selector */
    ShiftRegister<Byte> wbSelectorLatch;

    /** Latch for WB enable */
    ShiftRegister<Byte> wbEnableLatch;

    /** Latch for WB mux index */
    ShiftRegister<Integer> wbMuxIndexLatch;

    ShiftRegister<Byte> haltEnableLatch;

    WbLatches(Cycler cycler, String name) {
        wbSelectorLatch = new ShiftRegister<>(name + ".wbSelector", 2, (byte)0, cycler);
        wbEnableLatch = new ShiftRegister<>(name + ".wbEnable", 2, (byte)0, cycler);
        wbMuxIndexLatch = new ShiftRegister<>(name + ".wbMuxIndex", 2, 0, cycler);
        haltEnableLatch = new ShiftRegister<>(name + ".haltEnable", 2, (byte)0, cycler);
    }

    public Output<Byte> getHaltEnableLatch() {
        return haltEnableLatch.getOutput(1);
    }

    public void setHaltEnableLatch(Output<Byte> haltEnableInput) {
        haltEnableLatch.setInput(haltEnableInput);
    }

    /**
     * Setter for WB enable latch input
     * @param wbEnableInput wb enable latch input
     * @return WbLatches
     */
    public WbLatches setWbEnableInput(Output<Byte> wbEnableInput) {
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
        return wbMuxIndexLatch.getOutput(1);
    }

    /**
     * Getter for wb selector output
     * @return wb selector output
     */
    public Output<Byte> getWbSelectorOutput() {
        return wbSelectorLatch.getOutput(1);
    }

    /**
     * Getter for wb enable output
     * @return wb enable output
     */
    public Output<Byte> getWbEnableOutput() {
        return wbEnableLatch.getOutput(1);
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
