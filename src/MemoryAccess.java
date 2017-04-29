/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    /**
     * Data is located here
     */
    private Memory memory;

    /**
     * Data register
     */
    private Register<Integer> dataRegister;

    /**
     * Address register
     */
    private Register<Integer> addressRegister;

    private Register<Byte> dAddressRegister;

    private Register<Boolean> dEnableRegister;

    private Register<Integer> dControlRegister;

    /**
     * Constructor
     * @param memory main memory
     */
    MemoryAccess(Memory memory, Cycler cycler) {
        this.memory = memory;
    }

    /**
     * Initialize the data register and address register
     * @param dataInput data register input
     * @param nextPCInput address register input
     */
    public void init(Output<Integer> dataInput, Output<Integer> nextPCInput, Output<Integer> address,
                     Output<Byte> dAddressInput, Output<Boolean> enableInput, Output<Integer> controlInput) {
        // TODO: add correct enable inputs
        dAddressRegister.init(dAddressInput);
        dEnableRegister.init(enableInput);
        dControlRegister.init(controlInput);
        dataRegister.init(dataInput);
        addressRegister.init(nextPCInput);
        memory.init(dataRegister.getOutput(), addressRegister.getOutput(), null);
    }

    public Output<Integer> getD0Output() {
        return memory.getDataOutput();
    }

    public Output<Integer> getD1Output() {
        return dataRegister.getOutput();
    }

    public Output<Byte> getDAddressOutput() {
        return dAddressRegister.getOutput();
    }

    public Output<Boolean> getDEnableOutput() {
        return dEnableRegister.getOutput();
    }

    public Output<Integer> getDControlOutput() {
        return dControlRegister.getOutput();
    }
}
