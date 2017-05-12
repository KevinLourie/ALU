/**
 * Collection of 32 registers. Each register is referred to by its 5 bits.
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] registers = new int[32];

    /** D register selector. Picks a register to be read */
    private Output<Byte> dSelectorInput;

    /** S register selector. Picks another register to be read */
    private Output<Byte> sSelectorInput;

    /** T register selector. Picks a register to write to */
    private Output<Byte> tSelectorInput;

    /** D register input */
    private Output<Integer> dInput;

    /** S register output */
    private Output<Integer> sOutput;

    /** T register output */
    private Output<Integer> tOutput;

    /** Temporary D */
    private int tempD;

    /** Temporary selector of D register */
    private byte tempDSelector;

    /** Controls whether register bank is written to */
    private Output<Boolean> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    RegisterBank(Cycler cycler) {
        sOutput = () -> {
            // Fetching 2 bytes at a time
            byte address = sSelectorInput.read();
            int data = registers[address];
            System.out.printf("sOutput[%d] -> %d%n", address, data);
            return data;
        };
        tOutput = () -> {
            // Fetching 2 bytes at a time
            byte address = tSelectorInput.read();
            int data = registers[address];
            System.out.printf("tOutput[%d] -> %d%n", address, data);
            return data;
        };
        cycler.add(this);
    }

    /**
     * Setter for D selector input
     * @param dSelectorInput D selector input
     * @return register bank
     */
    public RegisterBank setDSelectorInput(Output<Byte> dSelectorInput) {
        this.dSelectorInput = dSelectorInput;
        return this;
    }

    /**
     * Setter for S selector input
     * @param addressSInput S selector input
     * @return register bank
     */
    public RegisterBank setSSelectorInput(Output<Byte> addressSInput) {
        this.sSelectorInput = addressSInput;
        return this;
    }

    /**
     * Setter for T selector input
     * @param addressTInput T selector input
     * @return
     */
    public RegisterBank setTSelectorInput(Output<Byte> addressTInput) {
        this.tSelectorInput = addressTInput;
        return this;
    }

    /**
     * Setter for input to register
     * @param dInput input to register
     * @return register bank
     */
    public RegisterBank setdInput(Output<Integer> dInput) {
        this.dInput = dInput;
        return this;
    }

    /**
     * Setter for enable input
     * @param enableInput enable input
     * @return register bank
     */
    public RegisterBank setEnableInput(Output<Boolean> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Write data to memory
     */
    @Override
    public void cycle() {
        if(tempEnable) {
            registers[tempDSelector] = tempD;
            System.out.printf("Store %d from %d%n", registers[tempDSelector], tempDSelector);
        }
    }

    @Override
    public void sense() {
        tempD = dInput.read();
        tempDSelector = dSelectorInput.read();
        tempEnable = enableInput.read();
    }

    /**
     * Getter for sOutput
     * @return sOutput
     */
    public Output<Integer> getSOutput() {
        return sOutput;
    }

    /**
     * Getter for tOutput
     * @return tOutput
     */
    public Output<Integer> getTOutput() {
        return tOutput;
    }
}