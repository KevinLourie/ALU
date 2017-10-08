/**
 * Created by kzlou on 7/8/2017.
 */
public abstract class Value extends Number {

    public String toString() {
        return String.format("%x", longValue());
    }

    @Override
    public float floatValue() {
        return intValue();
    }

    @Override
    public int intValue() {
        return (int)longValue();
    }

    @Override
    public double doubleValue() {
        return intValue();
    }

    public boolean booleanValue() {
        return intValue() != 0;
    }

    @Override
    public boolean equals(Object obj) {
        return longValue() == ((Number)obj).longValue();
    }

    public abstract Value clone();
}
