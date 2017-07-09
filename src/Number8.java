/**
 * Created by kzlou on 7/7/2017.
 */
public class Number8 extends Value {

    private byte n;

    public static final Number8 zero = new Number8(0, "constant");

    public static final Number8 one = new Number8(1, "constant");

    public Number8(int n, String src) {
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
    Value clone(String newSrc) {
        return new Number8(n, newSrc);
    }
}