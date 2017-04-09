import java.util.logging.Logger;

/**
 * Created by kzlou on 4/6/2017.
 */
public class Register32 extends Register implements Input<Integer> {

    Input<Integer> input;

    int data;

    Register32(String name) {
        super(name);
    }

    public void init(Input<Integer> a) {
        input = a;
    }

    public void cycle() {
        switch (registerOp) {
            case Store:
                data = input.read();
                System.out.printf(" -> %s %d%n", name, data);
                break;
            case Increment2:
                data = data + 2;
                System.out.println(name + " + 2 -> " + name);
                break;
            case Decrement2:
                data = data - 2;
                System.out.println(name + "- 2 -> " + name);
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