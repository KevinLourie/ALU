/**
 * Choose input for register.
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> {

    /**
     * Inputs to choose from
     */
    private Output<T>[] inputArray;

    /**
     * Location of desired input
     */
    private Output<Integer> index;

    /**
     * Desired input
     */
    private Output<T> muxOutput;

    Multiplexer() {
        /**
         * Return desired input
         * @return desired input
         */
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

    /**
     * Initialize multiplexer inputs
     * @param inputs inputs to multiplexer
     */
    public Multiplexer<T> setInputs(Output<T>... inputs) {
        this.inputArray = inputs;
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
