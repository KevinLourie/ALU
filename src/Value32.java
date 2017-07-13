/**
 * Created by kzlou on 7/7/2017.
 */
public class Value32 extends Value {

    private int n;

    private String src;

    public static final Value32 zero = new Value32(0, "constant");

    public Value32(int n, String src) {
        this.n = n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public String toString() {
        return src;
    }

    @Override
    Value clone(String src) {
        return new Value32(n, src);
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