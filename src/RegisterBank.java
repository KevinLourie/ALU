import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank {

    // Register array
    int[] arr = new int[32];

    Output<Byte> addressInput;

    Output<Byte> addressInput1;

    Output<Byte> addressInput2;

    Output<Integer> dataInput;

    Output<Integer> output1;

    Output<Integer> output2;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    RegisterBank() {
        output1 = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                byte address = addressInput1.read();
                int data = arr[address];
                System.out.printf(" %d]", address);
                return data;
            }
        };
        output2 = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                byte address = addressInput2.read();
                int data = arr[address];
                System.out.printf(" %d]", address);
                return data;
            }
        };
    }

    public void init(Output<Byte> addressInput1, Output<Byte> addressInput2, Output<Byte> addressInput, Output<Integer> dataInput) {
        this.addressInput1 = addressInput1;
        this.addressInput2 = addressInput2;
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

    public Output<Integer> getOutput1() {
        return output1;
    }

    public Output<Integer> getOutput2() {
        return output2;
    }
}
