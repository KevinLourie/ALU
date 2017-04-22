import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank {

    // Register array
    int[] arr = new int[32];

    // Address of register
    Output<Byte> addressInput;

    Output<Integer> dataInput;

    Output<Integer> output;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    RegisterBank() {
        output = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                byte address = addressInput.read();
                int data = arr[address];
                System.out.printf(" %d]", address);
                return data;
            }
        };
    }

    public void init(Output<Byte> addressInput, Output<Integer> dataInput) {
        this.addressInput = addressInput;
        this.dataInput = dataInput;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        int index = addressInput.read();
        arr[index] = dataInput.read();
        System.out.printf("Store %d from %d%n", arr[index], index);
    }

    public Output<Integer> getOutput() {
        return output;
    }
}
