/**
 * Choose input for register.
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> implements Output<T>, Input<Integer> {

    /**
     * Inputs to choose from
     */
    Output<T>[] inputArray;

    /**
     * Location of desired input
     */
    int index;

    public void init(Output<T>... muxOp) {
        this.inputArray = muxOp;
    }

    /**
     * Return desired input
     * @return desired input
     */
    @Override
    public T read() {
        return inputArray[index].read();
    }

    /**
     * Specify index of input
     * @param data index of input
     */
    @Override
    public void write(Integer data) {
        index = data;
    }
}
