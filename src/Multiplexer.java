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
    private Output<Integer> muxInput;

    /**
     * Desired input
     */
    private Output<T> muxOutput;

    Multiplexer() {
        /**
         * Return desired input
         * @return desired input
         */
        muxOutput = new Output<T>() {
            @Override
            public T read() {
                return inputArray[muxInput.read()].read();
            }
        };
    }

    /**
     * Initialize multiplexer input and multiplexer operator
     * @param muxInput multiplexer input
     * @param muxOp multiplexer operator
     */
    public void init(Output<Integer> muxInput, Output<T>... muxOp) {
        this.inputArray = muxOp;
        this.muxInput = muxInput;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return muxOutput;
    }
}
