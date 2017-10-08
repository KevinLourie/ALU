/**
 * Created by kzlou on 7/7/2017.
 */
public class Value64 extends Value {

    private long n;

    public static final Value64 zero = new Value64(0);

    public Value64(long n) {
        this.n = n;
    }

    @Override
    public Value clone() {
        return new Value64(n);
    }

    @Override
    public long longValue() {
        return n;
    }
}