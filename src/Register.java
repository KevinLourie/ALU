/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T extends Value> implements ICycle {

    /** Name of register */
    private String name;

    /** Data in register */
    private T data;

    /** Temporary data */
    private T tempData;

    /** Output of register */
    private Output<T> output;

    /** Input to register */
    private Output<T> input;

    /** Controls whether data is written to register */
    private Output<Value8> enableInput;

    /** Temporary enable */
    private Value8 tempEnable;

    Register(String name, T initial, Cycler cycler) {
        this.name = name;
        data = initial;
        output = () -> {
            return data;
        };
        cycler.add(this);
        // By default, the register is always enabled
        this.enableInput = new ConstantOutput(Value8.one);
    }

    /**
     * Check if a register's data has changed between cycles
     * @return true if it has changed, false is not
     */
    public String toStringDelta() {
        return tempData == null || tempData.equals(data) ? null : toString();
    }

    @Override
    public String toString() {
        return name + ":" + tempData.toString();
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register
     */
    public Register<T> setEnableInput(Output<Value8> enableInput) {
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
        if (tempEnable.booleanValue()) {
            data = (T)tempData.clone();
        }
    }

    @Override
    public void sense() {
        tempEnable = enableInput.read();
        if (tempEnable.booleanValue()) {
            T inputValue = input.read();
            tempData = inputValue;
        } else {
            tempData = null;
        }
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return output;
    }
}
