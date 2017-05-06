/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /** Latch for WB 0 */
    Register<Integer> wb0Latch;

    /** Latch for WB 1 */
    Register<Integer> wb1Latch;

    /** Latch for WB Selector */
    Register<Byte> wbSelectorLatch;

    /** Latch for WB enable */
    Register<Boolean> wbEnableLatch;

    /** Latch for WB mux index */
    Register<Integer> wbMuxIndexLatch;

    /** Choose what to write back */
    private Multiplexer<Integer> wbMux;

    /** Register bank */
    private RegisterBank registerBank;

    WriteBack(Cycler cycler, RegisterBank registerBank) {
        wbMux = new Multiplexer<>();
        this.registerBank = registerBank;
        wb0Latch = new Register<>("WriteBack.WB0", 0, cycler);
        wb1Latch = new Register<>("WriteBack.WB1", 0, cycler);
        wbSelectorLatch = new Register<>("WriteBack.wbSelector", (byte)0, cycler);
        wbEnableLatch = new Register<>("WriteBack.wbEnable", true, cycler);
        wbMuxIndexLatch = new Register<>("WriteBack.wbMuxIndex", 0, cycler);

        // Internal wiring
        wbMux
                .setIndexInput(wbMuxIndexLatch.getOutput())
                .setInputs(wb0Latch.getOutput(), wb1Latch.getOutput());
    }

    public WriteBack setWbInputs(Output<Integer> wb0Input, Output<Integer> wb1Input) {
        wbMux.setInputs(wb0Input, wb1Input);
        return this;
    }

    public WriteBack setWbSelectorInput(Output<Byte> wbSelectorInput) {
        registerBank.setAddressDInput(wbSelectorInput);
        return this;
    }

    public WriteBack setWbEnableInput(Output<Boolean> wbEnableInput) {
        registerBank.setEnableInput(wbEnableInput);
        return this;
    }

    public WriteBack setWbMuxIndexInput(Output<Integer> wbMuxIndexInput) {
        wbMux.setIndexInput(wbMuxIndexInput);
        return this;
    }

}