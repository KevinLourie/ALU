/**
 * Created by kzlou on 6/15/2017.
 */
public class Coprocessor {

    static public final byte multiply = 0;

    static public final byte divide = 1;

    ShiftRegister<Value64> hilo;

    Register<Value8> coprocessorOpLatch;

    Register<Value32> sLatch;

    Register<Value32> tLatch;

    /** Contains the upper 32 bits of the hilo register */
    Output<Value32> hiOutput;

    /** Contains the lower 32 bits of the hilo register */
    Output<Value32> loOutput;

    /** For multiplication and division */
    Mdu mdu;

    Coprocessor(Cycler cycler) {
        mdu = new Mdu();
        sLatch = new Register<>("Coprocessor.SLatch", Value32.zero, cycler);
        tLatch = new Register<>("Coprocessor.TLatch", Value32.zero, cycler);
        coprocessorOpLatch = new Register<>("Coprocessor Op", Value8.zero, cycler);
        hilo = new ShiftRegister<>("Coprocessor.HiLo", 2, Value64.zero, cycler);

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
    public Coprocessor setEnableInput(Output<Value8> enableInput) {
        hilo.setEnableInput(enableInput);
        return this;
    }

    public Coprocessor setCoprocessorOpInput(Output<Value8> coprocessorOpInput) {
        coprocessorOpLatch.setInput(coprocessorOpInput);
        return this;
    }

    public Coprocessor setSLatchInput(Output<Value32> sLatchInput) {
        sLatch.setInput(sLatchInput);
        return this;
    }

    public Coprocessor setTLatchInput(Output<Value32> tLatchInput) {
        tLatch.setInput(tLatchInput);
        return this;
    }
}
