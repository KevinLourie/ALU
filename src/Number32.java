/**
 * Created by kzlou on 7/7/2017.
 */
public class Number32 extends Value {

    private int n;

    private String src;

    public static final Number32 zero = new Number32(0, "constant");

    public Number32(int n, String src) {
        this.n = n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public String toString() {
        return src;
    }

    @Override
    Value clone(String newSrc) {
        return new Number32(n, String.format("%x=%s", n, newSrc));
    }

    @Override
    public int intValue() {
        return n;
    }

    @Override
    public long longValue() {
        return n;
    }
}