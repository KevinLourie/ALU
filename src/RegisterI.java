/**
 * Created by kzlou on 4/6/2017.
 */
public class RegisterI extends Register implements Input<Integer> {

    Input<Short> input;

    int data;

    RegisterI(String name) {
        super(name);
    }

    public void init(Input<Short> a) {
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
    public Integer read() {
        System.out.print(name);
        return data;
    }
}
