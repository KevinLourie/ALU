/**
 * Created by kzlou on 6/15/2017.
 */
public class Coprocessor {

    static public final byte multiply = 0;

    static public final byte divide = 1;

    ShiftRegister<Number64> hilo;

    Register<Number8> coprocessorOpLatch;

    Register<Number32> sLatch;

    Register<Number32> tLatch;

    /** Contains the upper 32 bits of the hilo register */
    Output<Number32> hiOutput;

    /** Contains the lower 32 bits of the hilo register */
    Output<Number32> loOutput;

    Mdu mdu;

    Coprocessor(Cycler cycler) {
        mdu = new Mdu();
        sLatch = new Register<>("Coprocessor.SLatch", Number32.zero, cycler);
        tLatch = new Register<>("Coprocessor.TLatch", Number32.zero, cycler);
        coprocessorOpLatch = new Register<>("Coprocessor Op", Number8.zero, cycler);
        hilo = new ShiftRegister<>("Coprocessor.HiLo", 2, Number64.zero, cycler);

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
    public Coprocessor setEnableInput(Output<Number8> enableInput) {
        hilo.setEnableInput(enableInput);
        return this;
    }

    public Coprocessor setCoprocessorOpInput(Output<Number8> coprocessorOpInput) {
        coprocessorOpLatch.setInput(coprocessorOpInput);
        return this;
    }

    public Coprocessor setSLatchInput(Output<Number32> sLatchInput) {
        sLatch.setInput(sLatchInput);
        return this;
    }

    public Coprocessor setTLatchInput(Output<Number32> tLatchInput) {
        tLatch.setInput(tLatchInput);
        return this;
    }
}
