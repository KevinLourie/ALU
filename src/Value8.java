/**
 * Created by kzlou on 7/7/2017.
 */
public class Value8 extends Value {

    private byte n;

    public static final Value8 zero = new Value8(0, "constant");

    public static final Value8 one = new Value8(1, "constant");

    public Value8(int n, String src) {
        this.n = (byte)n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public int intValue() {
        return n;
    }

    @Override
    public long longValue() {
        return n;
    }


    @Override
    Value clone(String src) {
        return new Value8(n, src);
    }
}