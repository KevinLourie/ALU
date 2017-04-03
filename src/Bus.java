import java.util.logging.Logger;

/**
 * Transfer data
 * Created by kzlou on 3/31/2017.
 */
public class Bus<T> implements Input<T>, Output<T>{

    /** Data to write or read */
    private T data;

    public final static Logger logger = Logger.getLogger(Bus.class.getName());

    @Override
    public void write(T data) {
        this.data = data;
        logger.fine("Write " + data);
    }

    @Override
    public T read() {
        logger.fine("Read " + data);
        return data;
    }

}
