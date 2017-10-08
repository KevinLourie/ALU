/**
 * Collection of 32 registers. Each register is referred to by its 5 bits.
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] registers;

    /** Write back register selector. Picks a register to be read */
    private Output<Value8> wbSelectorInput;

    /** S register selector. Picks another register to be read */
    private Output<Value8> sSelectorInput;

    /** T register selector. Picks a register to write to */
    private Output<Value8> tSelectorInput;

    /** Write back register input */
    private Output<Value32> wbInput;

    /** S register output */
    private Output<Value32> sOutput;

    /** T register output */
    private Output<Value32> tOutput;

    /** Temporary write back data */
    private Value32 tempWb;

    /** Temporary selector of write back register */
    private Value8 tempWbSelector;

    /** Controls whether register bank is written to */
    private Output<Value8> wbEnableInput;

    /** Temporary enable */
    private Value8 tempWbEnable;

    RegisterBank(Cycler cycler, int[] registers) {
        this.registers = registers;
        sOutput = () -> {
            // Fetch S register
            Value8 address = sSelectorInput.read();
            int data = registers[address.byteValue()];
            return new Value32(data);
        };
        tOutput = () -> {
            // Fetch T register
            Value8 address = tSelectorInput.read();
            int data = registers[address.byteValue()];
            return new Value32(data);
        };
        cycler.add(this);
    }

    /**
     * Setter for D selector input
     * @param wbSelectorInput Write back selector input
     * @return register bank
     */
    public RegisterBank setWbSelectorInput(Output<Value8> wbSelectorInput) {
        this.wbSelectorInput = wbSelectorInput;
        return this;
    }

    /**
     * Setter for S selector input
     * @param addressSInput S selector input
     * @return register bank
     */
    public RegisterBank setSSelectorInput(Output<Value8> addressSInput) {
        this.sSelectorInput = addressSInput;
        return this;
    }

    /**
     * Setter for T selector input
     * @param addressTInput T selector input
     * @return
     */
    public RegisterBank setTSelectorInput(Output<Value8> addressTInput) {
        this.tSelectorInput = addressTInput;
        return this;
    }

    /**
     * Setter for input to register
     * @param wbInput input to register
     * @return register bank
     */
    public RegisterBank setWbInput(Output<Value32> wbInput) {
        this.wbInput = wbInput;
        return this;
    }

    /**
     * Setter for enable input
     * @param wbEnableInput enable input
     * @return register bank
     */
    public RegisterBank setWbEnableInput(Output<Value8> wbEnableInput) {
        this.wbEnableInput = wbEnableInput;
        return this;
    }

    public String toStringDelta() {
        // Nothing has been written back
        if(tempWb == null) {
            return null;
        }
        final int wbSelectorValue = tempWbSelector.intValue();
        if(tempWb.equals(registers[wbSelectorValue])) {
            return null;
        }
        return "R" + wbSelectorValue + ":" + String.format("%x", tempWb.intValue());
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
        if (tempWbEnable.booleanValue()) {
            tempWb = wbInput.read();
            tempWbSelector = wbSelectorInput.read();
        } else {
            tempWb = null;
            tempWbSelector = null;
        }
    }

    /**
     * Getter for sOutput
     * @return sOutput
     */
    public Output<Value32> getSOutput() {
        return sOutput;
    }

    /**
     * Getter for tOutput
     * @return tOutput
     */
    public Output<Value32> getTOutput() {
        return tOutput;
    }
}