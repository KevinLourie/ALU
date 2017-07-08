/**
 * Collection of 32 registers. Each register is referred to by its 5 bits.
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] registers;

    /** Write back register selector. Picks a register to be read */
    private Output<Number8> wbSelectorInput;

    /** S register selector. Picks another register to be read */
    private Output<Number8> sSelectorInput;

    /** T register selector. Picks a register to write to */
    private Output<Number8> tSelectorInput;

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
    private Output<Number8> wbEnableInput;

    /** Temporary enable */
    private boolean tempWbEnable;

    RegisterBank(Cycler cycler, int[] registers) {
        this.registers = registers;
        sOutput = () -> {
            // Fetch S register
            Number8 address = sSelectorInput.read();
            int data = registers[address.byteValue()];
            System.out.printf("%x=S(%s) -> ", data, address);
            return data;
        };
        tOutput = () -> {
            // Fetch T register
            Number8 address = tSelectorInput.read();
            int data = registers[address.byteValue()];
            System.out.printf("%x=T(%s) -> ", data, address);
            return data;
        };
        cycler.add(this);
    }

    /**
     * Setter for D selector input
     * @param wbSelectorInput Write back selector input
     * @return register bank
     */
    public RegisterBank setWbSelectorInput(Output<Number8> wbSelectorInput) {
        this.wbSelectorInput = wbSelectorInput;
        return this;
    }

    /**
     * Setter for S selector input
     * @param addressSInput S selector input
     * @return register bank
     */
    public RegisterBank setSSelectorInput(Output<Number8> addressSInput) {
        this.sSelectorInput = addressSInput;
        return this;
    }

    /**
     * Setter for T selector input
     * @param addressTInput T selector input
     * @return
     */
    public RegisterBank setTSelectorInput(Output<Number8> addressTInput) {
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
    public RegisterBank setWbEnableInput(Output<Number8> wbEnableInput) {
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
        }
    }

    @Override
    public void sense() {
        Number8 enableN = wbEnableInput.read();
        tempWbEnable = enableN.byteValue() != 0;
        System.out.print("WB");
        if (tempWbEnable) {
            tempWb = wbInput.read();
            tempWbSelector = wbSelectorInput.read().byteValue();
            System.out.printf("(%d) #%x", tempWbSelector, tempWb);
        } else {
            tempWb = 0;
            tempWbSelector = -1;
        }
        System.out.printf(" enable=%s%n", enableN);
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