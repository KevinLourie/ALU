/**
 * Created by kzlou on 4/29/2017.
 */
public class Adder {

    /** Current number */
    private Output<Value32> input1;

    /** Second input to PC */
    private Output<Value32> input2;

    /** PC result */
    private Output<Value32> output;

    /**
     * Constructor
     */
    Adder() {
        output = new Output<Value32>() {
            @Override
            public Value32 read() {
                Value32 i1 = input1.read();
                Value32 i2 = input2.read();
                return new Value32(i1.intValue() + i2.intValue());
            }
        };
    }

    /**
     * Initialize precomputedOutput
     * @param input1 first input
     * @param input2 second input
     */
    public void init(Output<Value32> input1, Output<Value32> input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * Initialize first input
     * @param input1 first input
     */
    public Adder setInput1(Output<Value32> input1) {
        this.input1 = input1;
        return this;
    }

    /**
     * Initialize second input
     * @param input2 second input
     */
    public Adder setInput2(Output<Value32> input2) {
        this.input2 = input2;
        return this;
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<Value32> getOutput() {
        return output;
    }
}
