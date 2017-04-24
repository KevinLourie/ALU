/**
 * Created by kzlou on 4/23/2017.
 */
public abstract class Bus<IN, OUT> implements Output<OUT> {

    Output<IN> input;

    public void init(Output<IN> input) {
        this.input = input;
    }
}
