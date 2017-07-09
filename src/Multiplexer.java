/**
 * Choose input for register.
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T extends Value> {

    /** Inputs to choose from */
    private Output<T>[] inputArray;

    /** Location of desired input */
    private Output<Value8> index;

    /** Desired input */
    private Output<T> muxOutput;

    Multiplexer(int size) {
        /**
         * Return desired input
         * @return desired input
         */
        inputArray = new Output[size];
        // TODO: Fix logging
        muxOutput = () -> {
            Value8 indexValue = index.read();
            T inputValue = inputArray[indexValue.byteValue()].read();
            return (T)inputValue.clone(String.format("Mux(%s, %s)", inputValue, indexValue));
        };
    }

    /**
     * Initialize multiplexer index
     * @param index multiplexer input
     */
    public Multiplexer<T> setIndexInput(Output<Value8> index) {
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
