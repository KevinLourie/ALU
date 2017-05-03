import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] arr = new int[32];

    /** Address of D register */
    private Output<Byte> addressDInput;

    /** Address of S register */
    private Output<Byte> addressSInput;

    /** Address of T register */
    private Output<Byte> addressTInput;

    /** D register */
    private Output<Integer> dInput;

    /** S register */
    private Output<Integer> sOutput;

    /** T register */
    private Output<Integer> tOutput;

    /** Temporary D register */
    private int tempD;

    /** Temporary address of D register */
    private byte tempAddressD;

    /** Controls whether register bank is written to */
    private Output<Boolean> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    RegisterBank(Cycler cycler) {
        sOutput = () -> {
            System.out.print("[");
            // Fetching 2 bytes at a time
            byte address = addressSInput.read();
            int data = arr[address];
            System.out.printf(" %d]", address);
            return data;
        };
        tOutput = () -> {
            System.out.print("[");
            // Fetching 2 bytes at a time
            byte address = addressTInput.read();
            int data = arr[address];
            System.out.printf(" %d]", address);
            return data;
        };
        cycler.add(this);
    }

    public RegisterBank setAddressDInput(Output<Byte> addressDInput) {
        this.addressDInput = addressDInput;
        return this;
    }

    public RegisterBank setAddressSInput(Output<Byte> addressSInput) {
        this.addressSInput = addressSInput;
        return this;
    }

    public RegisterBank setAddressTInput(Output<Byte> addressTInput) {
        this.addressTInput = addressTInput;
        return this;
    }

    public RegisterBank setdInput(Output<Integer> dInput) {
        this.dInput = dInput;
        return this;
    }

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
            arr[tempAddressD] = tempD;
            System.out.printf("Store %d from %d%n", arr[tempAddressD], tempAddressD);
        }
    }

    @Override
    public void sense() {
        tempD = dInput.read();
        tempAddressD = addressDInput.read();
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