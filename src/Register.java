/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> implements ICycle {

    /**
     * Name of register
     */
    private String name;

    /**
     * Data type of register
     */
    private T data;

    private T tempData;

    /**
     * Output of register
     */
    private Output<T> output;

    private Output<T> input;

    private Output<Boolean> enableInput;

    private boolean tempEnable;

    Register(String name, T initial, Cycler cycler) {
        this.name = name;
        data = initial;
        output = () -> {
            System.out.print(name);
            return data;
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
     * Initializes input
     * @param input input
     */
    public void init(Output<T> input) {
        this.input = input;
        this.enableInput = new ConstantOutput(true);
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
