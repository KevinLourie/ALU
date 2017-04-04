import java.util.logging.Logger;

/**
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> implements Input<T>, Output<RegisterOp> {

    Input<T> input;

    T data;

    RegisterOp registerOp;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Register(Input<T> a) {
        input = a;
    }

    public void cycle() {
        switch (registerOp) {
            case Store:
                data = input.read();
                logger.fine("store " + data);
                break;
            case None:
                break;
        }
    }

    @Override
    public T read() {
        logger.fine(data.toString());
        return data;
    }

    @Override
    public void write(RegisterOp data) {
        logger.fine(data.toString());
        registerOp = data;
    }
}
