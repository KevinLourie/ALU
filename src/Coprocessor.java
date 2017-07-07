/**
 * Created by kzlou on 6/15/2017.
 */
public class Coprocessor {

    static public final byte multiply = 0;

    static public final byte divide = 1;

    ShiftRegister<Long> hilo;

    Register<Byte> coprocessorOpLatch;

    Register<Integer> sLatch;

    Register<Integer> tLatch;

    /** Contains the upper 32 bits of the hilo register */
    Output<Integer> hiOutput;

    /** Contains the lower 32 bits of the hilo register */
    Output<Integer> loOutput;

    Mdu mdu;

    Coprocessor(Cycler cycler) {
        mdu = new Mdu();
        sLatch = new Register<>("SLatch", 0, cycler);
        tLatch = new Register<>("SLatch", 0, cycler);
        coprocessorOpLatch = new Register<>("Coprocessor Op", (byte)0, cycler);
        hilo = new ShiftRegister<>("HiLo", 2, (long)0, cycler);

        // Internal wiring
        hilo.setInput(mdu.getOutput());
        mdu.setOperation(coprocessorOpLatch.getOutput());
        mdu.setInput0(sLatch.getOutput());
        mdu.setInput1(tLatch.getOutput());
    }

    /**
     * Setter for enable input for hilo register
     * @param enableInput enable input for hilo register
     * @return coprocessor
     */
    public Coprocessor setEnableInput(Output<Byte> enableInput) {
        hilo.setEnableInput(enableInput);
        return this;
    }

    public Coprocessor setCoprocessorOpInput(Output<Byte> coprocessorOpInput) {
        coprocessorOpLatch.setInput(coprocessorOpInput);
        return this;
    }

    public Coprocessor setSLatchInput(Output<Integer> sLatchInput) {
        sLatch.setInput(sLatchInput);
        return this;
    }

    public Coprocessor setTLatchInput(Output<Integer> tLatchInput) {
        tLatch.setInput(tLatchInput);
        return this;
    }
}
