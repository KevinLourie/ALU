/**
 * Input is two numbers. Output is dOutput of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class Alu {

    /** First number */
    private Output<Integer> sInput;

    /** Second number */
    private Output<Integer> tInput;

    /** Result number */
    private Output<Integer> dOutput;

    /** ALU control */
    private Output<Byte> aluOp;

    Alu() {
        dOutput = new Output<Integer>() {
            /**
             * Read 2 numbers. Perform operation specified by the aluOp field.
             * @return dOutput
             */
            @Override
            public Integer read() {
                int s = sInput.read();
                int t = tInput.read();
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
                System.out.printf("%s%n", d);
                return d;
            }
        };
    }

    /**
     * Initialize operands and aluOp
     * @param operand1 operand1
     * @param operand2 operand2
     * @param operator aluOp
     */
    public void init(Output operand1, Output operand2, Output operator) {
        this.sInput = operand1;
        this.tInput = operand2;
        this.aluOp = operator;
    }
}