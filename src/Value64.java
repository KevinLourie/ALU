/**
 * Created by kzlou on 7/7/2017.
 */
public class Value64 extends Value {

    private long n;

    private String src;

    public static final Value64 zero = new Value64(0, "constant");

    public Value64(long n, String src) {
        this.n = n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public String toString() {
        return src;
    }

    @Override
    Value clone(String src) {
        return new Value64(n, src);
    }

    @Override
    public int intValue() {
        return (int)n;
    }

    @Override
    public long longValue() {
        return n;
    }
}