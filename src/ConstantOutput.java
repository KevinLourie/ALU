/**
 * Created by kzlou on 4/28/2017.
 */
public class ConstantOutput<T> implements Output<T> {

    /** Constant */
    private T constant;

    /** True constant */
    private static ConstantOutput<Number8> trueOutput = new ConstantOutput<>(new Number8(1, "Constant"));

    /**
     * Getter for true output
     * @return true output
     */
    public static ConstantOutput<Number8> getTrueOutput() {
        return trueOutput;
    }

    /**
     * Constructor
     * @param constant constant number (4)
     */
    ConstantOutput(T constant) {
        this.constant = constant;
    }

    /**
     * Read
     * @return constant
     */
    @Override
    public T read() {
        return constant;
    }
}
