import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank {

    // Register array
    int[] arr = new int[32];

    Output<Byte> addressDInput;

    Output<Byte> addressSInput;

    Output<Byte> addressTInput;

    Output<Integer> dInput;

    Output<Integer> sOutput;

    Output<Integer> tOutput;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    RegisterBank() {
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
    }

    /**
     * Initialize address registers and data register
     * @param addressInput1 input to first address register
     * @param addressInput2 input to second address register
     * @param addressInput input to address register
     * @param dataInput input to data register
     */
    public void init(Output<Byte> addressInput1, Output<Byte> addressInput2, Output<Byte> addressInput, Output<Integer> dataInput) {
        this.addressSInput = addressInput1;
        this.addressTInput = addressInput2;
        this.addressDInput = addressInput;
        this.dInput = dataInput;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        int index = addressDInput.read();
        arr[index] = dInput.read();
        System.out.printf("Store %d from %d%n", arr[index], index);
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
