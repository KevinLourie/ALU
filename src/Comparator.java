/**
 * Adds a constant to an input
 * Created by kzlou on 4/22/2017.
 */
public class Comparator {

    /** First number */
    private Output<Integer> input1;

    /** Second number */
    private Output<Integer> input2;

    /** 0 if numbers are same, 1 if not */
    private Output<Integer> output;

    /**
     * Constructor
     */
    Comparator() {
        output = new Output<Integer>() {
            @Override
            public Integer read() {
                return input1.read() == input2.read() ? 1 : 0;
            }
        };
    }

    /**
     * Initialize input
     * @param input1 first input
     * @param input2 second input
     */
    public void init(Output<Integer> input1, Output<Integer> input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<Integer> getOutput() {
        return output;
    }
}
