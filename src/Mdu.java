/**
 * Input is two numbers. Output is output of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class Mdu {

    /** First number */
    private Output<Integer> input0;

    /** Second number */
    private Output<Integer> input1;

    /** Result number */
    private Output<Long> output;

    /** MDU control */
    private Output<Byte> mduOp;

    Mdu() {
        output = new Output<Long>() {
            /**
             * Read 2 numbers. Perform operation specified by the mduOp field.
             * @return output
             */
            @Override
            public Long read() {
                int s = input0.read();
                int t = input1.read();
                long d = 0;
                switch (mduOp.read()) {
                    case AluOp.Multiply:
                        d = s * t;
                        break;
                    case AluOp.Divide:
                        d = s / t;
                        break;
                }
                return d;
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

    public Output<Long> getOutput() {
        return output;
    }
}