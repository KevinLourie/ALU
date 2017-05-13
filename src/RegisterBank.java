/**
 * Collection of 32 registers. Each register is referred to by its 5 bits.
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] registers = new int[32];

    /** Write back register selector. Picks a register to be read */
    private Output<Byte> wbSelectorInput;

    /** S register selector. Picks another register to be read */
    private Output<Byte> sSelectorInput;

    /** T register selector. Picks a register to write to */
    private Output<Byte> tSelectorInput;

    /** Write back register input */
    private Output<Integer> wbInput;

    /** S register output */
    private Output<Integer> sOutput;

    /** T register output */
    private Output<Integer> tOutput;

    /** Temporary write back data */
    private int tempWb;

    /** Temporary selector of write back register */
    private byte tempWbSelector;

    /** Controls whether register bank is written to */
    private Output<Boolean> wbEnableInput;

    /** Temporary enable */
    private boolean tempWbEnable;

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
     * @param wbSelectorInput Write back selector input
     * @return register bank
     */
    public RegisterBank setWbSelectorInput(Output<Byte> wbSelectorInput) {
        this.wbSelectorInput = wbSelectorInput;
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
     * @param wbInput input to register
     * @return register bank
     */
    public RegisterBank setWbInput(Output<Integer> wbInput) {
        this.wbInput = wbInput;
        return this;
    }

    /**
     * Setter for enable input
     * @param wbEnableInput enable input
     * @return register bank
     */
    public RegisterBank setWbEnableInput(Output<Boolean> wbEnableInput) {
        this.wbEnableInput = wbEnableInput;
        return this;
    }

    /**
     * Write data to memory
     */
    @Override
    public void cycle() {
        if(tempWbEnable) {
            registers[tempWbSelector] = tempWb;
            System.out.printf("RegisterBank[%d] <- %d from %s%n", tempWbSelector, tempWb, wbInput);
        }
    }

    @Override
    public void sense() {
        tempWbEnable = wbEnableInput.read();
        tempWb = tempWbEnable ? wbInput.read() : 0;
        tempWbSelector = tempWbEnable ? wbSelectorInput.read() : -1;
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