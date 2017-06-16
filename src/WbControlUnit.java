/**
 * Created by kzlou on 5/7/2017.
 */
public class WbControlUnit {

    /** Latch for WB Selector */
    ShiftRegister<Byte> wbSelectorLatch;

    /** Latch for WB enable */
    ShiftRegister<Byte> wbEnableLatch;

    /** Latch for WB mux index */
    ShiftRegister<Integer> wbMuxIndexLatch;

    ShiftRegister<Byte> haltEnableLatch;

    /** Selector of the S register */
    Output<Byte> sSelectorInput;

    /** Selector of the T register */
    Output<Byte> tSelectorInput;

    /** S mux index. 0 uses the register bank, 1 uses the result from the ALU, and 2 for the write back */
    Output<Integer> sMuxIndexOutput;

    /** T mux index */
    Output<Integer> tMuxIndexOutput;

    public Output<Integer> getsMuxIndexOutput() {
        return sMuxIndexOutput;
    }

    public Output<Integer> gettMuxIndexOutput() {
        return tMuxIndexOutput;
    }

    WbControlUnit(Cycler cycler) {
        wbSelectorLatch = new ShiftRegister<>("WbControlUnit.wbSelector", 2, (byte)0, cycler);
        wbEnableLatch = new ShiftRegister<>("WbControlUnit.wbEnable", 2, (byte)0, cycler);
        wbMuxIndexLatch = new ShiftRegister<>("WbControlUnit.wbMuxIndex", 2, 0, cycler);
        haltEnableLatch = new ShiftRegister<>("WbControlUnit.haltEnable", 2, (byte)0, cycler);
        sMuxIndexOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                if(wbMuxIndexLatch.getOutput(0).read() == 1 && wbEnableLatch.getOutput(0).read() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(0).read()) {
                    return 1;
                }
                else if(wbEnableLatch.getOutput(1).read() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(1).read()) {
                    return 2;
                }
                return 0;
            }
        };
        tMuxIndexOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                if(wbMuxIndexLatch.getOutput(0).read() == 1 && wbEnableLatch.getOutput(0).read() == (byte)1 && tSelectorInput.read() == wbSelectorLatch.getOutput(0).read()) {
                    return 1;
                }
                else if(wbEnableLatch.getOutput(1).read() == (byte)1 && sSelectorInput.read() == wbSelectorLatch.getOutput(1).read()) {
                    return 2;
                }
                return 0;
            }
        };

    }

    public WbControlUnit setSSelectorInput(Output<Byte> sSelectorInput) {
        this.sSelectorInput = sSelectorInput;
        return this;
    }

    public WbControlUnit setTSelectorInput(Output<Byte> tSelectorInput) {
        this.tSelectorInput = tSelectorInput;
        return this;
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
    public WbControlUnit setWbEnableInput(Output<Byte> wbEnableInput) {
        wbEnableLatch.setInput(wbEnableInput);
        return this;
    }

    /**
     * Setter for WB selector latch input
     * @param wbSelectorInput wb selector latch input
     * @return WbLatches
     */
    public WbControlUnit setWbSelectorInput(Output<Byte> wbSelectorInput) {
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
     * @param wbControlUnit latch inputs
     */
    public void setLatchInputs(WbControlUnit wbControlUnit) {
        setWbEnableInput(wbControlUnit.getWbEnableOutput());
        setWbMuxIndexInput(wbControlUnit.getWbMuxIndexOutput());
        setWbSelectorInput(wbControlUnit.getWbSelectorOutput());
        setHaltEnableLatch(wbControlUnit.getHaltEnableLatch());
    }

}
