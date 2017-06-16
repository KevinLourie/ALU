/**
 * Created by kzlou on 6/15/2017.
 */
public class Coprocessor {

    static public final byte multiply = 0;

    static public final byte divide = 1;

    ShiftRegister<Long> hilo;

    Output<Integer> hiOutput;

    Output<Integer> loOutput;

    Coprocessor(Cycler cycler) {
        hilo = new ShiftRegister<>("HiLo", 2, (long)0, cycler);
        hiOutput = () -> (int)(hilo.getOutput(1).read() >>> 32);
        loOutput = () -> (int)(hilo.getOutput(1).read() & 0xFFFF);
    }

    public Coprocessor setEnableInput(Output<Byte> enableInput) {
        hilo.setEnableInput(enableInput);
        return this;
    }
}
