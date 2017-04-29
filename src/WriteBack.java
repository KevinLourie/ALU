/**
 * Store result in a register
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /**
     * Choose what to write back
     */
    private Multiplexer<Integer> dMux;

    private RegisterBank registerBank;

    WriteBack(Cycler cycler, RegisterBank registerBank) {
        dMux = new Multiplexer<>();
        this.registerBank = registerBank;
    }

    /**
     * Initialize memory register and write back register
     *
     * @param d0Input
     * @param d1Input
     * @param dAddressInput
     * @param dEnable
     * @param dControl
     */
    public void init(Output<Integer> d0Input, Output<Integer> d1Input, Output<Byte> dAddressInput,
                     Output<Boolean> dEnable, Output<Integer> dControl) {
        registerBank.initWrite(dAddressInput, dMux.getOutput(), dEnable);
        dMux.init(dControl, d0Input, d1Input);
    }

}