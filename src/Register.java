/**
 * Created by kzlou on 4/1/2017.
 */
public class Register implements Input<RegisterOp> {

    RegisterOp registerOp;

    String name;

    Register(String name) {
        this.name = name;
    }

    @Override
    public void write(RegisterOp data) {
        registerOp = data;
    }
}
