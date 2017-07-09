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
    private Output<Number32> wbInput;

    /** S register output */
    private Output<Number32> sOutput;

    /** T register output */
    private Output<Number32> tOutput;

    /** Temporary write back data */
    private Number32 tempWb;

    /** Temporary selector of write back register */
    private Number8 tempWbSelector;

    /** Controls whether register bank is written to */
    private Output<Number8> wbEnableInput;

    /** Temporary enable */
    private Number8 tempWbEnable;

    RegisterBank(Cycler cycler, int[] registers) {
        this.registers = registers;
        sOutput = () -> {
            // Fetch S register
            Number8 address = sSelectorInput.read();
            int data = registers[address.byteValue()];
            return new Number32(data, String.format("S(%s)", address));
        };
        tOutput = () -> {
            // Fetch T register
            Number8 address = tSelectorInput.read();
            int data = registers[address.byteValue()];
            return new Number32(data, String.format("T(%s)", address));
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
    public RegisterBank setWbInput(Output<Number32> wbInput) {
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
        if(tempWbEnable.booleanValue()) {
            registers[tempWbSelector.intValue()] = tempWb.intValue();
        }
    }

    @Override
    public void sense() {
        tempWbEnable = wbEnableInput.read();
        System.out.print("WB");
        if (tempWbEnable.booleanValue()) {
            tempWb = wbInput.read();
            tempWbSelector = wbSelectorInput.read();
            System.out.printf("(%s) <- %s", tempWbSelector, tempWb);
        } else {
            tempWb = null;
            tempWbSelector = null;
        }
        System.out.printf(" enable(%s)%n", tempWbEnable);
    }

    /**
     * Getter for sOutput
     * @return sOutput
     */
    public Output<Number32> getSOutput() {
        return sOutput;
    }

    /**
     * Getter for tOutput
     * @return tOutput
     */
    public Output<Number32> getTOutput() {
        return tOutput;
    }
}