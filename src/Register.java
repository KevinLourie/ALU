/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register implements Input<RegisterOp> {

    /**
     * Register operation
     */
    RegisterOp registerOp;

    /**
     * Name of register
     */
    String name;

    Register(String name) {
        this.name = name;
    }

    /**
     * Choose register operation
     * @param data register operation
     */
    @Override
    public void write(RegisterOp data) {
        registerOp = data;
    }
}
