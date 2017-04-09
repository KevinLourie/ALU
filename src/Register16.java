import java.util.logging.Logger;

/**
 * Created by kzlou on 4/6/2017.
 */
public class Register16 extends Register implements Input<Short> {

    Input<Short> input;

    short data;

    Register16(String name) {
        super(name);
    }

    public void init (Input<Short> a) {
        input = a;
    }

    public void cycle() {
        switch (registerOp) {
            case Store:
                data = input.read();
                System.out.printf(" -> %s %d%n", name, data);
                break;
            case None:
                break;
        }
    }

    @Override
    public Short read() {
        System.out.print(name);
        return data;
    }
}