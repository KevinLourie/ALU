/**
 * Choose input for register.
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> implements Output<T> {

    /**
     * Inputs to choose from
     */
    Output<T>[] inputArray;

    /**
     * Location of desired input
     */
    Output<Integer> index;

    public void init(Output<Integer> index, Output<T>... muxOp) {
        this.inputArray = muxOp;
        this.index = index;
    }

    /**
     * Return desired input
     * @return desired input
     */
    @Override
    public T read() {
        return inputArray[index.read()].read();
    }
}
