/**
 * Created by kzlou on 4/23/2017.
 */
public abstract class Bus<IN, OUT> implements Output<OUT> {

    Output<IN> input;

    public Bus<IN, OUT> setInput(Output<IN> input) {
        this.input = input;
        return this;
    }
}
