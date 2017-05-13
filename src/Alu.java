/**
 * Input is two numbers. Output is output of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class Alu {

    /** First number */
    private Output<Integer> input0;

    /** Second number */
    private Output<Integer> input1;

    /** Result number */
    private Output<Integer> output;

    /** ALU control */
    private Output<Byte> aluOp;

    Alu() {
        output = new Output<Integer>() {
            /**
             * Read 2 numbers. Perform operation specified by the aluOp field.
             * @return output
             */
            @Override
            public Integer read() {
                int s = input0.read();
                int t = input1.read();
                int d = 0;
                switch (aluOp.read()) {
                    case AluOp.Add:
                        d = s + t;
                        break;
                    case AluOp.Subtract:
                        d = s - t;
                        break;
                    case AluOp.Multiply:
                        d = s * t;
                        break;
                    case AluOp.Divide:
                        d = s / t;
                        break;
                    case AluOp.ShiftRightLogical:
                        // TODO: Fix by using shamt
                        d = t;
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
    public Alu setInput0(Output input0) {
        this.input0 = input0;
        return this;
    }

    /**
     * Initialize second input
     * @param input1 second input
     * @return Alu
     */
    public Alu setInput1(Output input1) {
        this.input1 = input1;
        return this;
    }

    /**
     * Initialize operator
     * @param operator operator
     * @return Alu
     */
    public Alu setOperation(Output operator) {
        this.aluOp = operator;
        return this;
    }

    public Output<Integer> getOutput() {
        return output;
    }
}