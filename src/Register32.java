/**
 * Accepts an integer input.
 * Created by kzlou on 4/6/2017.
 */
public class Register32 extends Register implements Output<Integer> {

    /**
     * Input to register
     */
    Output<Integer> input;

    /**
     * Data in input
     */
    int data;

    Register32(String name) {
        super(name);
    }

    public void init(Output<Integer> a) {
        input = a;
    }

    /**
     * Either write to register, or point to next or previous element
     */
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
                System.out.println(name + " - 2 -> " + name);
                break;
            case Increment:
                data++;
                System.out.println(name + " + 1 -> " + name);
                break;
            case Decrement:
                data--;
                System.out.println(name + " - 1 -> " + name);
                break;
            case Clear:
                data = 0;
                System.out.println(name + " = 0" + name);
                break;
            case None:
                break;
        }
    }

    /**
     * Get data stored in register
     * @return data stored in register
     */
    @Override
    public Integer read() {
        System.out.print(name);
        return data;
    }
}