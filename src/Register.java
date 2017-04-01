/**
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> {

    Input<T> input;

    Output<T> output;

    Input<RegisterOp> registerOp;

    Register(Input<T> a, Output<T> b, Input<RegisterOp> registerOp) {
        input = a;
        output = b;
        this.registerOp = registerOp;
    }

    public void cycle() {
        T data = null;
        switch (registerOp.read()) {
            case Read:
                data = input.read();
                break;
            case Write:
                output.write(data);
                break;
        }
    }

}
