/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> implements ICycle {

    /** Name of register */
    private String name;

    /** Data type of register */
    private T data;

    /** Temporary data */
    private T tempData;

    /** Output of register */
    private Output<T> output;

    /** Input to register */
    private Output<T> input;

    /** Controls whehter data is written to register */
    private Output<Boolean> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    Register(String name, T initial, Cycler cycler) {
        this.name = name;
        data = initial;
        output = () -> {
            System.out.printf("%s -> %s%n", name, data);
            return data;
        };
        cycler.add(this);
        this.enableInput = new ConstantOutput(true);
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register
     */
    public Register<T> setEnableInput(Output<Boolean> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Initializes input
     * @param input input
     */
    public Register<T> setInput(Output<T> input) {
        this.input = input;
        return this;
    }

    /**
     * Store data in register
     */
    @Override
    public void cycle() {
        if (tempEnable) {
            data = tempData;
            System.out.printf("%s <- %s from %s%n", name, data, input);
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
