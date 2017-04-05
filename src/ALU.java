import java.util.logging.Logger;

/**
 * Created by kzlou on 3/31/2017.
 */
public class ALU implements Input<Integer>, Output<ALUOp> {

    /**
     * First bus
     */
    private Input<Integer> a;

    /**
     * Second bus
     */
    private Input<Integer> b;

    /**
     * ALU control
     */
    private ALUOp operator;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    ALU(Input a, Input b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer read() {
        int operand1 = a.read();
        int operand2 = b.read();
        int result = 0;
        switch (operator) {
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
        }
        logger.fine(String.valueOf(result));
        return result;
    }

    @Override
    public void write(ALUOp data) {
        logger.fine(data.toString());
        operator = data;
    }
}
