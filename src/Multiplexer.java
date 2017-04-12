/**
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> implements Output<T>, Input<Integer> {

    Output<T>[] inputArray;

    int index;

    public void init(Output<T>... muxOp) {
        this.inputArray = muxOp;
    }

    @Override
    public T read() {
        return inputArray[index].read();
    }

    @Override
    public void write(Integer data) {
        index = data;
    }
}
