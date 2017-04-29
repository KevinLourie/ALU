/**
 * Input is two numbers. Output is dOutput of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class ALU {

    private Output<Boolean> zeroFlagOutput;

    private boolean zeroFlag;

    /**
     * First number
     */
    private Output<Integer> sInput;

    /**
     * Second number
     */
    private Output<Integer> tInput;

    /**
     * Resulting number
     */
    private Output<Integer> dOutput;

    /**
     * ALU control
     */
    private Output<AluOp> operator;

    ALU() {
        dOutput = new Output<Integer>() {
            /**
             * Read 2 numbers. Perform operation specified by the operator field.
             * @return dOutput
             */
            @Override
            public Integer read() {
                int s = sInput.read();
                int t = tInput.read();
                int d = 0;
                switch (operator.read()) {
                    case Add:
                        d = s + t;
                        break;
                    case Subtract:
                        d = s - t;
                        break;
                    case Multiply:
                        d = s * t;
                        break;
                    case Divide:
                        d = s / t;
                        break;
                    case Right:
                        d = t;
                }
                zeroFlag = d == 0;
                System.out.printf("%s%n", d);
                return d;
            }
        };

        zeroFlagOutput = new Output<Boolean>() {
            @Override
            public Boolean read() {
                return zeroFlag;
            }
        };
    }

    public Output<Boolean> getZeroFlagOutput() {
        return zeroFlagOutput;
    }

    /**
     * Initialize operands and operator
     * @param operand1 operand1
     * @param operand2 operand2
     * @param operator operator
     */
    public void init(Output operand1, Output operand2, Output operator) {
        this.sInput = operand1;
        this.tInput = operand2;
        this.operator = operator;
    }
}