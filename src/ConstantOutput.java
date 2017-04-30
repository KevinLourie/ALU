/**
 * Created by kzlou on 4/28/2017.
 */
public class ConstantOutput<T> implements Output<T> {

    private T constant;

    private static ConstantOutput<Boolean> trueOutput = new ConstantOutput<>(true);

    public static ConstantOutput<Boolean> getTrueOutput() {
        return trueOutput;
    }

    ConstantOutput(T constant) {
        this.constant = constant;
    }

    @Override
    public T read() {
        return constant;
    }
}
