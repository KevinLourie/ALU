/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    Register<Integer> wb0Latch;
    Register<Integer> wb1Latch;
    Register<Byte> wbSelectorLatch;
    Register<Boolean> wbEnableLatch;
    Register<Integer> wbMuxIndexLatch;

    /**
     * Choose what to write back
     */
    private Multiplexer<Integer> wbMux;

    /**
     * Register bank
     */
    private RegisterBank registerBank;

    WriteBack(Cycler cycler, RegisterBank registerBank) {
        wbMux = new Multiplexer<>();
        this.registerBank = registerBank;
        wb0Latch = new Register<>("WB0", 0, cycler);
        wb1Latch = new Register<>("WB1", 0, cycler);
        wbSelectorLatch = new Register<>("WB Selector", (byte)0, cycler);
        wbEnableLatch = new Register<>("WB Enable", true, cycler);
        wbMuxIndexLatch = new Register<>("WB Mux Index", 0, cycler);

        // Internal wiring
        wbMux
                .setIndexInput(wbMuxIndexLatch.getOutput())
                .setInputs(wb0Latch.getOutput(), wb1Latch.getOutput());
    }

    public void setWbInputs(Output<Integer> wb0Input, Output<Integer> wb1Input) {
        wbMux.setInputs(wb0Input, wb1Input);
    }

    public void setWbSelectorInput(Output<Byte> wbSelectorInput) {
        registerBank.setAddressDInput(wbSelectorInput);
    }

    public void setWbEnableInput(Output<Boolean> wbEnableInput) {
        registerBank.setEnableInput(wbEnableInput);
    }

    public void setWbMuxIndexInput(Output<Integer> wbMuxIndexInput) {
        wbMux.setIndexInput(wbMuxIndexInput);
    }

}