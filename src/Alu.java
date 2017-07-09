/**
 * Input is two numbers. Output is output of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class Alu {

    /** First number */
    private Output<Number32> input0;

    /** Second number */
    private Output<Number32> input1;

    /** Result number */
    private Output<Number32> output;

    /** ALU control */
    private Output<Number8> aluOp;

    Alu() {
        output = new Output<Number32>() {
            /**
             * Read 2 numbers. Perform operation specified by the aluOp field.
             * @return output
             */
            @Override
            public Number32 read() {
                // Number32 s32 = input0.read();
                // int s = s32;

                Number32 sN = input0.read();
                int s = sN.intValue();
                Number32 tN = input1.read();
                int t = tN.intValue();
                int d = 0;
                Number8 opN = aluOp.read();
                switch (opN.intValue()) {
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
                // return new Number32(d, String.format("%d=Alu(%s, %s)", d, s32, t32));
                return new Number32(d, String.format("Alu(%s, %s, %s)", sN, tN, opN));
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

    public Output<Number32> getOutput() {
        return output;
    }
}