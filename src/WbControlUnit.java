/**
 * Created by kzlou on 5/7/2017.
 */
public class WbControlUnit {

    /** Latch for WB Selector */
    ShiftRegister<Number8> wbSelectorLatch;

    /** Latch for WB enable */
    ShiftRegister<Number8> wbEnableLatch;

    /** Latch for WB mux index */
    ShiftRegister<Number8> wbMuxIndexLatch;

    ShiftRegister<Number8> haltEnableLatch;

    /** Selector of the S register */
    Output<Number8> sSelectorInput;

    /** Selector of the T register */
    Output<Number8> tSelectorInput;

    /** S mux index. 0 uses the register bank, 1 uses the result from the ALU, and 2 for the write back */
    Output<Number8> sMuxIndexOutput;

    /** T mux index */
    Output<Number8> tMuxIndexOutput;

    Output<Number8> goOutput;

    public Output<Number8> getSMuxIndexOutput() {
        return sMuxIndexOutput;
    }

    public Output<Number8> getTMuxIndexOutput() {
        return tMuxIndexOutput;
    }

    WbControlUnit(Cycler cycler) {
        wbSelectorLatch = new ShiftRegister<>("WbControlUnit.wbSelector", 2, Number8.zero, cycler);
        wbEnableLatch = new ShiftRegister<>("WbControlUnit.wbEnable", 2, Number8.zero, cycler);
        wbMuxIndexLatch = new ShiftRegister<>("WbControlUnit.wbMuxIndex", 2, Number8.zero, cycler);
        haltEnableLatch = new ShiftRegister<>("WbControlUnit.haltEnable", 2, Number8.zero, cycler);
        sMuxIndexOutput = new Output<Number8>() {
            @Override
            public Number8 read() {
                return computeMuxIndex(sSelectorInput.read(), "S");
            }
        };
        tMuxIndexOutput = new Output<Number8>() {
            @Override
            public Number8 read() {
                return computeMuxIndex(tSelectorInput.read(), "T");
            }
        };
        goOutput = new Output<Number8>() {
            @Override
            public Number8 read() {
                return Number8.one;
            }
        };
    }

    /**
     * Choose the correct value for S or T in case of data hazards
     *
     * @param selector either S or T selector
     * @param name either S or T
     * @return mux index for S or T
     */
    private Number8 computeMuxIndex(Number8 selector, String name) {
        Number8 wbMuxIndex = wbMuxIndexLatch.getOutput(0).read();
        Number8 wbEnable0 = wbEnableLatch.getOutput(0).read();
        Number8 wbSelector0 = wbSelectorLatch.getOutput(0).read();
        Number8 wbEnable1 = wbEnableLatch.getOutput(1).read();
        Number8 wbSelector1 = wbSelectorLatch.getOutput(1).read();
        String src = String.format("%sMuxIndex(%s, %s, %s, %s, %s, %s)", name, wbMuxIndex, selector, wbEnable0, wbSelector0, wbEnable1, wbSelector1);
        if(wbMuxIndex.intValue() == 1 && wbEnable0.intValue() == 1 && selector == wbSelector0) {
            return new Number8(1, src);
        }
        else if(wbEnable1.intValue() == 1 && selector == wbSelector1) {
            return new Number8(2, src);
        }
        return new Number8(0, src);
    }


    public WbControlUnit setSSelectorInput(Output<Number8> sSelectorInput) {
        this.sSelectorInput = sSelectorInput;
        return this;
    }

    public WbControlUnit setTSelectorInput(Output<Number8> tSelectorInput) {
        this.tSelectorInput = tSelectorInput;
        return this;
    }

    public Output<Number8> getHaltEnableLatch() {
        return haltEnableLatch.getOutput(1);
    }

    public void setHaltEnableLatch(Output<Number8> haltEnableInput) {
        haltEnableLatch.setInput(haltEnableInput);
    }

    /**
     * Setter for WB enable latch input
     * @param wbEnableInput wb enable latch input
     * @return WbLatches
     */
    public WbControlUnit setWbEnableInput(Output<Number8> wbEnableInput) {
        wbEnableLatch.setInput(wbEnableInput);
        return this;
    }

    /**
     * Setter for WB selector latch input
     * @param wbSelectorInput wb selector latch input
     * @return WbLatches
     */
    public WbControlUnit setWbSelectorInput(Output<Number8> wbSelectorInput) {
        wbSelectorLatch.setInput(wbSelectorInput);
        return this;
    }

    /**
     * Setter for WB mux index latch input
     * @param wbMuxIndexInput wb mux index latch input
     * @return WbLatches
     */
    public WbControlUnit setWbMuxIndexInput(Output<Number8> wbMuxIndexInput) {
        wbMuxIndexLatch.setInput(wbMuxIndexInput);
        return this;
    }

    /**
     * Getter for wb mux index output
     * @return wb mux index output
     */
    public Output<Number8> getWbMuxIndexOutput() {
        return wbMuxIndexLatch.getOutput(1);
    }

    /**
     * Getter for wb selector output
     * @return wb selector output
     */
    public Output<Number8> getWbSelectorOutput() {
        return wbSelectorLatch.getOutput(1);
    }

    /**
     * Getter for wb enable output
     * @return wb enable output
     */
    public Output<Number8> getWbEnableOutput() {
        return wbEnableLatch.getOutput(1);
    }

    public Output<Number8> getGoOutput() {
        return goOutput;
    }

    /**
     * Setter for latch inputs
     * @param wbControlUnit latch inputs
     */
    public void setLatchInputs(WbControlUnit wbControlUnit) {
        setWbEnableInput(wbControlUnit.getWbEnableOutput());
        setWbMuxIndexInput(wbControlUnit.getWbMuxIndexOutput());
        setWbSelectorInput(wbControlUnit.getWbSelectorOutput());
        setHaltEnableLatch(wbControlUnit.getHaltEnableLatch());
    }

}
