/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /**
     * Choose what to write back
     */
    private Multiplexer<Integer> dMux;

    /**
     * Register bank
     */
    private RegisterBank registerBank;

    WriteBack(Cycler cycler, RegisterBank registerBank) {
        dMux = new Multiplexer<>();
        this.registerBank = registerBank;
    }

    /**
     * Initialize memory register and write back register
     *
     * @param wb0Input
     * @param wb1Input
     * @param wbSelectorInput
     * @param wbEnable
     * @param wbMuxIndex
     */
    public void init(Output<Integer> wb0Input, Output<Integer> wb1Input, Output<Byte> wbSelectorInput,
                     Output<Boolean> wbEnable, Output<Integer> wbMuxIndex) {
        registerBank.initWrite(wbSelectorInput, dMux.getOutput(), wbEnable);
        dMux.init(wbMuxIndex, wb0Input, wb1Input);
    }

}