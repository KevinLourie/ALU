/**
 * Created by kzlou on 4/29/2017.
 */
public class Adder {

    /** Current number */
    private Output<Number32> input1;

    /** Second input to PC */
    private Output<Number32> input2;

    /** PC result */
    private Output<Number32> output;

    /**
     * Constructor
     */
    Adder() {
        output = new Output<Number32>() {
            @Override
            public Number32 read() {
                Number32 i1 = input1.read();
                Number32 i2 = input2.read();
                return new Number32(i1.intValue() + i2.intValue(), String.format("Adder(%s, %s)", i1, i2));
            }
        };
    }

    /**
     * Initialize inputs
     * @param input1 first input
     * @param input2 second input
     */
    public void init(Output<Number32> input1, Output<Number32> input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * Initialize first input
     * @param input1 first input
     */
    public Adder setInput1(Output<Number32> input1) {
        this.input1 = input1;
        return this;
    }

    /**
     * Initialize second input
     * @param input2 second input
     */
    public Adder setInput2(Output<Number32> input2) {
        this.input2 = input2;
        return this;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<Number32> getOutput() {
        return output;
    }
}
