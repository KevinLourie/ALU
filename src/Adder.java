/**
 * Adds a constant to an input
 * Created by kzlou on 4/22/2017.
 */
public class Adder {

    /**
     * Current number
     */
    private Output<Integer> input;

    /**
     * Next number
     */
    private Output<Integer> output;

    /**
     * Constructor
     * @param offset constant
     */
    Adder(int offset) {
        output = new Output<Integer>() {
            @Override
            public Integer read() {
                return input.read() + offset;
            }
        };
    }

    /**
     * Initialize input
     *
     * @param input number we're adding to
     */
    public void init(Output<Integer> input) {
        this.input = input;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<Integer> getOutput() {
        return output;
    }
}
