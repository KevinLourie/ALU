/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> implements ICycle {

    /**
     * Name of register
     */
    String name;

    /**
     * Data type of register
     */
    T data;

    T tempData;

    /**
     * Output of register
     */
    Output<T> output;

    Output<T> input;

    Output<Boolean> enableInput;

    boolean tempEnable;

    Register(String name, T initial, Cycler cycler) {
        this.name = name;
        data = initial;
        output = new Output<T>() {
            @Override
            public T read() {
                System.out.print(name);
                return data;
            }
        };
        cycler.add(this);
    }

    /**
     * Initializes input
     * @param input input
     */
    public void init(Output<T> input, Output<Boolean> enableInput) {
        this.input = input;
        this.enableInput = enableInput;
    }

    /**
     * Store data in register
     */
    @Override
    public void cycle() {
        if (tempEnable) {
            data = tempData;
            System.out.printf(" -> %s (%s)%n", name, data);
        }
    }

    @Override
    public void sense() {
        tempData = input.read();
        tempEnable = enableInput.read();
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return output;
    }
}
