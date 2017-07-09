/**
 * Created by kzlou on 7/8/2017.
 */
public abstract class Value extends Number {

    protected String src;

    public String toString() {
        return src;
    }

    @Override
    public float floatValue() {
        return intValue();
    }

    @Override
    public double doubleValue() {
        return intValue();
    }

    public boolean booleanValue() {
        return intValue() != 0;
    }

    abstract Value clone(String newSrc);
}
