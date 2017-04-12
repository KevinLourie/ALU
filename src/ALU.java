import java.util.logging.Logger;

/**
 * Created by kzlou on 3/31/2017.
 */
public class ALU implements Output<Short>, Input<ALUOp> {

    /**
     * First bus
     */
    private Output<Short> a;

    /**
     * Second bus
     */
    private Output<Short> b;

    /**
     * ALU control
     */
    private ALUOp operator;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    public void init(Output a, Output b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Short read() {
        short operand1 = a.read();
        short operand2 = b.read();
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
        return (short)result;
    }

    @Override
    public void write(ALUOp data) {
        operator = data;
    }
}
