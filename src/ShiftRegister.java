/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class ShiftRegister<T extends Value> implements ICycle {

    /** Name of register */
    private String name;

    /** Data type of register */
    private T[] data;

    /** Temporary data */
    private T[] tempData;

    /** Output of register */
    private Output<T>[] output;

    /** Input to register */
    private Output<T> input;

    /** Controls whehter data is written to register */
    private Output<Value8> enableInput;

    /** Temporary enable */
    private Value8 tempEnable;

    ShiftRegister(String name, int size, T initial, Cycler cycler) {
        this.name = name;
        data = (T[]) new Value[size];
        for(int i = 0; i < size; i++) {
            data[i] = initial;
        }
        tempData = (T[]) new Value[size];
        output = new Output[size];
        for(int i = 0; i < size; i++) {
            final int j = i;
            output[i] = () -> {
                return data[j];
            };
        }
        cycler.add(this);
        this.enableInput = new ConstantOutput<>(Value8.one);
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register
     */
    public ShiftRegister<T> setEnableInput(Output<Value8> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Initializes input
     * @param input input
     */
    public ShiftRegister<T> setInput(Output<T> input) {
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
            data = tempData.clone();
        }
    }

    @Override
    public void sense() {
        tempEnable = enableInput.read();
        if (tempEnable.booleanValue()) {
            T inputValue = input.read();
            tempData[0] = (T)inputValue.clone();
            for(int i = 1; i < data.length; i++) {
                tempData[i] = (T)data[i-1].clone();
            }
        } else {
            tempData = null;
        }
    }

    public String toStringDelta() {
        return tempData == null || tempData.equals(data) ? "" : toString();
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput(int index) {
        return output[index];
    }

    public Output<T>[] getOutputs() {
        return output.clone();
    }
}
