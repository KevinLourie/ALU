/**
 * Created by kzlou on 7/7/2017.
 */
public class Number64 extends Value {

    private long n;

    private String src;

    public static final Number64 zero = new Number64(0, "constant");

    public Number64(long n, String src) {
        this.n = n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public String toString() {
        return src;
    }

    @Override
    Value clone(String newSrc) {
        return new Number64(n, newSrc);
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