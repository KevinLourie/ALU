/**
 * Collection of 32 registers. Each register is referred to by its 5 bits.
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] registers;

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

    RegisterBank(Cycler cycler, int[] registers) {
        this.registers = registers;
        sOutput = () -> {
            // Fetch S register
            byte address = sSelectorInput.read();
            int data = registers[address];
            System.out.printf("S[%d] -> ", address);
            return data;
        };
        tOutput = () -> {
            // Fetch T register
            byte address = tSelectorInput.read();
            int data = registers[address];
            System.out.printf("T[%d] -> ", address);
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
        }
    }

    @Override
    public void sense() {
        tempWbEnable = wbEnableInput.read();
        if (tempWbEnable) {
            tempWb = wbInput.read();
            tempWbSelector = wbSelectorInput.read();
            System.out.printf("%x -> WB[%d] from %s%n", tempWb, tempWbSelector, wbInput);
        } else {
            tempWb = 0;
            tempWbSelector = -1;
            System.out.println("WB write disabled");
        }
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