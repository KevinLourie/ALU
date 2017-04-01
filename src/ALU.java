/**
 * Created by kzlou on 3/31/2017.
 */
public class ALU {

    /** First bus */
    private Input<Integer> a;

    /** Second bus */
    private Input<Integer> b;

    /** Third bus */
    private Output<Integer> c;

    /** ALU control */
    private Input<Operator> operator;

    ALU(Input a, Input b, Output c, Input<Operator> aluControl) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.operator = aluControl;
    }

    public void cycle() {
        int operand1 = a.read();
        int operand2 = b.read();
        int result = 0;
        switch (operator.read()) {
            case Add: result = operand1 + operand2;
            break;
            case Subtract: result = operand1 - operand2;
            break;
            case Multiply: result = operand1 * operand2;
            break;
            case Divide: result = operand1 / operand2;
            break;
        }
        c.write(result);
    }

}
