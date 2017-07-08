/**
 * Created by kzlou on 7/7/2017.
 */
public class Number8 extends Number {

    private byte n;

    private String src;

    public Number8(int n, String src) {
        this.n = (byte)n;
        this.src = String.format("%x=%s", this.n, src);
    }

    @Override
    public String toString() {
        return src;
    }

    @Override
    public int intValue() {
        return n;
    }

    @Override
    public float floatValue() {
        return n;
    }

    @Override
    public double doubleValue() {
        return n;
    }

    @Override
    public long longValue() {
        return n;
    }
}