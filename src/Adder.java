/**
 * Adds a constant to an input
 * Created by kzlou on 4/22/2017.
 */
public class Adder {

    private Output<Integer> input;

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

    public void init(Output<Integer> input) {
        this.input = input;
    }

    public Output<Integer> getOutput() {
        return output;
    }
}
