/**
 * Transfer data
 * Created by kzlou on 3/31/2017.
 */
public class Bus<T> implements Input<T>, Output<T>{

    /** Data to write or read */
    private T data;

    @Override
    public void write(T data) {
        this.data = data;
    }

    @Override
    public T read() {
        return data;
    }

}
