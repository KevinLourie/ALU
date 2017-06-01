/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /** Latch for WB 0 */
    Register<Integer> wb0Latch;

    /** Latch for WB 1 */
    Register<Integer> wb1Latch;

    Register<Boolean> haltLatch;

    /** Choose what to write back */
    private Multiplexer<Integer> wbMux;

    WriteBack(Cycler cycler) {
        wbMux = new Multiplexer<>();
        wb0Latch = new Register<>("WriteBack.WB0", 0, cycler);
        wb1Latch = new Register<>("WriteBack.WB1", 0, cycler);

        // Internal wiring
        wbMux.setInputs(wb0Latch.getOutput(), wb1Latch.getOutput());
    }

    public WriteBack setWb0Input(Output<Integer> wb0Input) {
        wb0Latch.setInput(wb0Input);
        return this;
    }

    public WriteBack setWb1Input(Output<Integer> wb1Input) {
        wb1Latch.setInput(wb1Input);
        return this;
    }

    public Output<Integer> getWbOutput() {
        return wbMux.getOutput();
    }

    public WriteBack setMuxIndexInput(Output<Integer> muxIndexInput) {
        wbMux.setIndexInput(muxIndexInput);
        return this;
    }
}