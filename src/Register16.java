/**
 * Created by kzlou on 4/6/2017.
 */
public class Register16 extends Register implements Output<Short> {

    Output<Short> input;

    short data;

    Register16(String name) {
        super(name);
    }

    public void init (Output<Short> a) {
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