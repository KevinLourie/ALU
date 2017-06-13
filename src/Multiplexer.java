/**
 * Choose input for register.
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> {

    /** Inputs to choose from */
    private Output<T>[] inputArray;

    /** Location of desired input */
    private Output<Integer> index;

    /** Desired input */
    private Output<T> muxOutput;

    Multiplexer(int size) {
        /**
         * Return desired input
         * @return desired input
         */
        inputArray = new Output[size];
        muxOutput = () -> inputArray[index.read()].read();
    }

    /**
     * Initialize multiplexer index
     * @param index multiplexer input
     */
    public Multiplexer<T> setIndexInput(Output<Integer> index) {
        this.index = index;
        return this;
    }

    public Multiplexer<T> setInput(int index, Output<T> input) {
        inputArray[index] = input;
        return this;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return muxOutput;
    }
}
