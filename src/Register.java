/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T extends Number> implements ICycle {

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
    private Output<Number8> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    Register(String name, T initial, Cycler cycler) {
        this.name = name;
        data = initial;
        output = () -> {
            System.out.printf("%s %s -> ", name, data);
            return data;
        };
        cycler.add(this);
        this.enableInput = new ConstantOutput(new Number8(1, "Constant"));
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register
     */
    public Register<T> setEnableInput(Output<Number8> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Initializes input
     * @param input input
     */
    public Register<T> setInput(Output<T> input) {
        System.out.printf("%s.setInput(%s)%n", name, input);
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
        }
    }

    @Override
    public void sense() {
        Number8 enableN = enableInput.read();
        tempEnable = enableN.byteValue() != 0;
        System.out.print(name);
        if (tempEnable) {
            tempData = input.read();
            System.out.printf(" %s", tempData);
        } else {
            tempData = null;
        }
        System.out.printf(" enable=%s%n", enableN);
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return output;
    }
}
