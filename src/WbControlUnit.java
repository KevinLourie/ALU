/**
 * Created by kzlou on 5/7/2017.
 */
public class WbControlUnit {

    /** Latch for WB Selector */
    ShiftRegister<Number8> wbSelectorLatch;

    /** Latch for WB enable */
    ShiftRegister<Number8> wbEnableLatch;

    /** Latch for WB mux index */
    ShiftRegister<Integer> wbMuxIndexLatch;

    ShiftRegister<Number8> haltEnableLatch;

    /** Selector of the S register */
    Output<Number8> sSelectorInput;

    /** Selector of the T register */
    Output<Number8> tSelectorInput;

    /** S mux index. 0 uses the register bank, 1 uses the result from the ALU, and 2 for the write back */
    Output<Integer> sMuxIndexOutput;

    /** T mux index */
    Output<Integer> tMuxIndexOutput;

    Output<Number8> goOutput;

    public Output<Integer> getsMuxIndexOutput() {
        return sMuxIndexOutput;
    }

    public Output<Integer> gettMuxIndexOutput() {
        return tMuxIndexOutput;
    }

    WbControlUnit(Cycler cycler) {
        wbSelectorLatch = new ShiftRegister<>("WbControlUnit.wbSelector", 2, new Number8(0, "Constant"), cycler);
        wbEnableLatch = new ShiftRegister<>("WbControlUnit.wbEnable", 2, new Number8(0, "Constant"), cycler);
        wbMuxIndexLatch = new ShiftRegister<>("WbControlUnit.wbMuxIndex", 2, 0, cycler);
        haltEnableLatch = new ShiftRegister<>("WbControlUnit.haltEnable", 2, new Number8(0, "Constant"), cycler);
        sMuxIndexOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                if(wbMuxIndexLatch.getOutput(0).read() == 1 && wbEnableLatch.getOutput(0).read().byteValue() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(0).read()) {
                    return 1;
                }
                else if(wbEnableLatch.getOutput(1).read().byteValue() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(1).read()) {
                    return 2;
                }
                return 0;
            }
        };
        tMuxIndexOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                if(wbMuxIndexLatch.getOutput(0).read() == 1 && wbEnableLatch.getOutput(0).read().byteValue() == (byte)1 && tSelectorInput.read() == wbSelectorLatch.getOutput(0).read()) {
                    return 1;
                }
                else if(wbEnableLatch.getOutput(1).read().byteValue() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(1).read()) {
                    return 2;
                }
                return 0;
            }
        };
        goOutput = new Output<Number8>() {
            @Override
            public Number8 read() {
                return new Number8(1, "Constant");
            }
        };
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
    public WbControlUnit setWbMuxIndexInput(Output<Integer> wbMuxIndexInput) {
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
