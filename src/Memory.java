import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dataInput. You can read and write to the memory. The addressInput comes from the MAR and the dataInput comes
 * from the MBR. The dataInput is retrieved using its addressInput.
 * Created by kzlou on 4/3/2017.
 */
public class Memory {

    // Byte array
    Integer[] arr = new Integer[2^22];

    // Address of byte
    Output<Integer> addressInput;

    // Data to read
    Output<Integer> dataInput;

    Output<Integer> output;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Memory() {
        output = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 2 bytes at a time
                int address = addressInput.read();
                int data = arr[address/4];
                System.out.printf(" %d]", address);
                return data;
            }
        };
    }

    public void init(Output<Integer> data, Output<Integer> address) {
        this.dataInput = data;
        this.addressInput = address;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        // Fetching 2 bytes at a time
        int index = addressInput.read()/4;
        arr[index] = dataInput.read();
        System.out.printf("Store %d from %d %n", arr[index], index);
    }

    public void readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int i = 0;
        while((line = bufferedReader.readLine()) != null) {
            arr[i] = Integer.parseInt(line);
            i++;
        }
    }
}
