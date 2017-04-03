import java.util.logging.Logger;

/**
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> {

    Input<T> input;

    Output<T> output;

    T data;

    Input<RegisterOp> registerOp;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Register(Input<T> a, Output<T> b, Input<RegisterOp> registerOp) {
        input = a;
        output = b;
        this.registerOp = registerOp;
    }

    public void cycle() {
        switch (registerOp.read()) {
            case Read:
                data = input.read();
                logger.fine("Read " + data);
                break;
            case Write:
                output.write(data);
                logger.fine("Write " + data);
                break;
        }
    }

}
