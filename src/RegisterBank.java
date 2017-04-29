import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank implements ICycle {

    // Register array
    private int[] arr = new int[32];

    private Output<Byte> addressDInput;

    private Output<Byte> addressSInput;

    private Output<Byte> addressTInput;

    private Output<Integer> dInput;

    private Output<Integer> sOutput;

    private Output<Integer> tOutput;

    private int tempD;

    private byte tempAddressD;

    private Output<Boolean> enableInput;

    private boolean tempEnable;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    RegisterBank(Cycler cycler) {
        sOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                byte address = addressSInput.read();
                int data = arr[address];
                System.out.printf(" %d]", address);
                return data;
            }
        };
        tOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                byte address = addressTInput.read();
                int data = arr[address];
                System.out.printf(" %d]", address);
                return data;
            }
        };
        cycler.add(this);
    }

    /**
     * Initialize address registers and data register
     * @param addressInput input to address register
     * @param dInput input to data register
     */
    public void initWrite(Output<Byte> addressInput, Output<Integer> dInput,  Output<Boolean> enableInput) {
        this.addressDInput = addressInput;
        this.dInput = dInput;
        this.enableInput = enableInput;
    }

    public void initRead(Output<Byte> addressSInput, Output<Byte> addressTInput) {
        this.addressSInput = addressSInput;
        this.addressTInput = addressTInput;
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
    public Output<Integer> getsOutput() {
        return sOutput;
    }

    /**
     * Getter for tOutput
     * @return tOutput
     */
    public Output<Integer> gettOutput() {
        return tOutput;
    }
}