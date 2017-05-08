/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /** Latch for WB 0 */
    Register<Integer> wb0Latch;

    /** Latch for WB 1 */
    Register<Integer> wb1Latch;

    /** Choose what to write back */
    private Multiplexer<Integer> wbMux;
    private Output<Byte> wbSelectorOutput;
    private Output<Boolean> wbEnableOutput;

    WriteBack(Cycler cycler) {
        wbMux = new Multiplexer<>();
        wb0Latch = new Register<>("WriteBack.WB0", 0, cycler);
        wb1Latch = new Register<>("WriteBack.WB1", 0, cycler);

        // Internal wiring
        wbMux.setInputs(wb0Latch.getOutput(), wb1Latch.getOutput());
    }

    public WriteBack setWbDataInputs(Output<Integer> wb0Input, Output<Integer> wb1Input) {
        wbMux.setInputs(wb0Input, wb1Input);
        return this;
    }

    public Output<Byte> getWbSelectorOutput() {
        return wbSelectorOutput;
    }

    public Output<Boolean> getWbEnableLatch() {
        return wbEnableOutput;
    }

    public WriteBack setWbControlInputs(WbLatches wbLatches) {
        this.wbSelectorOutput = wbLatches.getWbSelectorOutput();
        this.wbEnableOutput = wbLatches.getWbEnableOutput();
        wbMux.setIndexInput(wbLatches.getWbMuxIndexOutput());
        return this;
    }
}