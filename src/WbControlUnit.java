/**
 * Created by kzlou on 5/7/2017.
 */
public class WbControlUnit {

    /** Latch for WB Selector */
    ShiftRegister<Value8> wbSelectorLatch;

    /** Latch for WB enable */
    ShiftRegister<Value8> wbEnableLatch;

    /** Latch for WB mux index */
    ShiftRegister<Value8> wbMuxIndexLatch;

    ShiftRegister<Value8> haltEnableLatch;

    /** Selector of the S register */
    Output<Value8> sSelectorInput;

    /** Selector of the T register */
    Output<Value8> tSelectorInput;

    /** S mux index. 0 uses the register bank, 1 uses the result from the ALU, and 2 for the write back */
    Output<Value8> sMuxIndexOutput;

    /** T mux index */
    Output<Value8> tMuxIndexOutput;

    Output<Value8> goOutput;

    public Output<Value8> getSMuxIndexOutput() {
        return sMuxIndexOutput;
    }

    public Output<Value8> getTMuxIndexOutput() {
        return tMuxIndexOutput;
    }

    WbControlUnit(Cycler cycler) {
        wbSelectorLatch = new ShiftRegister<>("WbControlUnit.wbSelector", 2, Value8.zero, cycler);
        wbEnableLatch = new ShiftRegister<>("WbControlUnit.wbEnable", 2, Value8.zero, cycler);
        wbMuxIndexLatch = new ShiftRegister<>("WbControlUnit.wbMuxIndex", 2, Value8.zero, cycler);
        haltEnableLatch = new ShiftRegister<>("WbControlUnit.haltEnable", 2, Value8.zero, cycler);
        sMuxIndexOutput = new Output<Value8>() {
            @Override
            public Value8 read() {
                return computeMuxIndex(sSelectorInput.read(), "S");
            }
        };
        tMuxIndexOutput = new Output<Value8>() {
            @Override
            public Value8 read() {
                return computeMuxIndex(tSelectorInput.read(), "T");
            }
        };
        goOutput = new Output<Value8>() {
            @Override
            public Value8 read() {
                return Value8.one;
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
    private Value8 computeMuxIndex(Value8 selector, String name) {
        Value8 wbMuxIndex = wbMuxIndexLatch.getOutput(0).read();
        Value8 wbEnable0 = wbEnableLatch.getOutput(0).read();
        Value8 wbSelector0 = wbSelectorLatch.getOutput(0).read();
        Value8 wbEnable1 = wbEnableLatch.getOutput(1).read();
        Value8 wbSelector1 = wbSelectorLatch.getOutput(1).read();
        String src = String.format("%sMuxIndex(%s, %s, %s, %s, %s, %s)", name, wbMuxIndex, selector, wbEnable0, wbSelector0, wbEnable1, wbSelector1);
        if(wbMuxIndex.intValue() == 1 && wbEnable0.intValue() == 1 && selector == wbSelector0) {
            return new Value8(1, src);
        }
        else if(wbEnable1.intValue() == 1 && selector == wbSelector1) {
            return new Value8(2, src);
        }
        return new Value8(0, src);
    }


    public WbControlUnit setSSelectorInput(Output<Value8> sSelectorInput) {
        this.sSelectorInput = sSelectorInput;
        return this;
    }

    public WbControlUnit setTSelectorInput(Output<Value8> tSelectorInput) {
        this.tSelectorInput = tSelectorInput;
        return this;
    }

    public Output<Value8> getHaltEnableLatch() {
        return haltEnableLatch.getOutput(1);
    }

    public void setHaltEnableLatch(Output<Value8> haltEnableInput) {
        haltEnableLatch.setInput(haltEnableInput);
    }

    /**
     * Setter for WB enable latch input
     * @param wbEnableInput wb enable latch input
     * @return WbLatches
     */
    public WbControlUnit setWbEnableInput(Output<Value8> wbEnableInput) {
        wbEnableLatch.setInput(wbEnableInput);
        return this;
    }

    /**
     * Setter for WB selector latch input
     * @param wbSelectorInput wb selector latch input
     * @return WbLatches
     */
    public WbControlUnit setWbSelectorInput(Output<Value8> wbSelectorInput) {
        wbSelectorLatch.setInput(wbSelectorInput);
        return this;
    }

    /**
     * Setter for WB mux index latch input
     * @param wbMuxIndexInput wb mux index latch input
     * @return WbLatches
     */
    public WbControlUnit setWbMuxIndexInput(Output<Value8> wbMuxIndexInput) {
        wbMuxIndexLatch.setInput(wbMuxIndexInput);
        return this;
    }

    /**
     * Getter for wb mux index output
     * @return wb mux index output
     */
    public Output<Value8> getWbMuxIndexOutput() {
        return wbMuxIndexLatch.getOutput(1);
    }

    /**
     * Getter for wb selector output
     * @return wb selector output
     */
    public Output<Value8> getWbSelectorOutput() {
        return wbSelectorLatch.getOutput(1);
    }

    /**
     * Getter for wb enable output
     * @return wb enable output
     */
    public Output<Value8> getWbEnableOutput() {
        return wbEnableLatch.getOutput(1);
    }

    public Output<Value8> getGoOutput() {
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
