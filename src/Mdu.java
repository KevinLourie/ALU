/**
 * Input is two numbers. Output is output of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class Mdu {

    /** First number */
    private Output<Value32> input0;

    /** Second number */
    private Output<Value32> input1;

    /** Result number */
    private Output<Value64> output;

    /** MDU control */
    private Output<Value8> mduOp;

    Mdu() {
        output = new Output<Value64>() {
            /**
             * Read 2 numbers. Perform operation specified by the mduOp field.
             * @return output
             */
            @Override
            public Value64 read() {
                int s = input0.read().intValue();
                int t = input1.read().intValue();
                long d = 0;
                switch (mduOp.read().byteValue()) {
                    case AluOp.Multiply:
                        d = s * t;
                        break;
                    case AluOp.Divide:
                        d = s / t;
                        break;
                }
                return new Value64(d, "Mdu");
            }
        };
    }

    /**
     * Initialize first input
     * @param input0 first input
     * @return Alu
     */
    public Mdu setInput0(Output input0) {
        this.input0 = input0;
        return this;
    }

    /**
     * Initialize second input
     * @param input1 second input
     * @return Alu
     */
    public Mdu setInput1(Output input1) {
        this.input1 = input1;
        return this;
    }

    /**
     * Initialize operator
     * @param operator operator
     * @return Alu
     */
    public Mdu setOperation(Output operator) {
        this.mduOp = operator;
        return this;
    }

    public Output<Value64> getOutput() {
        return output;
    }
}