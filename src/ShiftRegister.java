/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class ShiftRegister<T extends Number> implements ICycle {

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
    private Output<Byte> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    ShiftRegister(String name, int size, T initial, Cycler cycler) {
        this.name = name;
        data = (T[]) new Number[size];
        for(int i = 0; i < size; i++) {
            data[i] = initial;
        }
        tempData = (T[]) new Number[size];
        output = new Output[size];
        for(int i = 0; i < size; i++) {
            final int j = i;
            output[i] = () -> {
                System.out.printf("%s #%x -> ", name, data[j]);
                return data[j];
            };
        }
        cycler.add(this);
        this.enableInput = new ConstantOutput<>((byte)1);
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register
     */
    public ShiftRegister<T> setEnableInput(Output<Byte> enableInput) {
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
        if (tempEnable) {
            data = tempData.clone();
        }
    }

    @Override
    public void sense() {
        tempEnable = enableInput.read() != 0;
        if (tempEnable) {
            tempData[0] = input.read();
            System.out.printf(" %s #%x", name, tempData[0]);
            for(int i = 1; i < data.length; i++) {
                tempData[i] = data[i-1];
                System.out.printf(" #%x", tempData[i]);
            }
            System.out.printf(" from %s", input);
        } else {
            tempData = null;
        }
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
