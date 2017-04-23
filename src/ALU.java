/**
 * Input is two numbers. Output is result of two numbers.
 * Created by kzlou on 3/31/2017.
 */
public class ALU {

    /**
     * First number
     */
    private Output<Integer> input1;

    /**
     * Second number
     */
    private Output<Integer> input2;

    private Output<Integer> result;

    /**
     * ALU control
     */
    private Output<AluOp> operator;

    ALU() {
        result = new Output<Integer>() {
            /**
             * Read 2 numbers. Perform operation specified by the operator field.
             * @return result
             */
            @Override
            public Integer read() {
                int operand1 = input1.read();
                int operand2 = input2.read();
                int result = 0;
                switch (operator.read()) {
                    case Add:
                        result = operand1 + operand2;
                        break;
                    case Subtract:
                        result = operand1 - operand2;
                        break;
                    case Multiply:
                        result = operand1 * operand2;
                        break;
                    case Divide:
                        result = operand1 / operand2;
                        break;
                    case Right:
                        result = operand2;
                }
                System.out.printf("%s%n", result);
                return result;
            }
        };
    }

    public void init(Output operand1, Output operand2, Output operator) {
        this.input1 = operand1;
        this.input2 = operand2;
        this.operator = operator;
    }
}