/**
 * Created by kzlou on 7/7/2017.
 */
public class Value32 extends Value {

    private int n;

    public static final Value32 zero = new Value32(0);

    public Value32(int n) {
        this.n = n;
    }

    @Override
    public Value clone() {
        return new Value32(n);
    }

    @Override
    public long longValue() {
        return 0xffffffffL & (long)n;
    }
}