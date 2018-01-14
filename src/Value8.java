/**
 * Created by kzlou on 7/7/2017.
 */
public class Value8 extends Value {

    private byte n;

    public static final Value8 zero = new Value8(0);

    public static final Value8 one = new Value8(1);

    public static final Value8 two = new Value8(2);

    public Value8(int n) {
        this.n = (byte)n;
    }

    @Override
    public long longValue() {
        return 0xffL & (long)n;
    }


    @Override
    public Value clone() {
        return new Value8(n);
    }
}