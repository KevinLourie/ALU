import java.util.logging.Logger;

/**
 * Transfer dataOutput
 * Created by kzlou on 3/31/2017.
 */
public class Bus<T> implements Output<T>, Input<T> {

    /** Data to write or read */
    private T data;

    public final static Logger logger = Logger.getLogger(Bus.class.getName());

    @Override
    public void write(T data) {
        this.data = data;
        logger.fine(data.toString());
    }

    @Override
    public T read() {
        logger.fine(data.toString());
        return data;
    }

}
