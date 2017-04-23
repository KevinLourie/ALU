import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dataInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dataInput comes
 * from the MBR. The dataInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class Memory {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Output<Integer> dataOutput;

    Output<Integer> instructionOutput;

    /* Byte array */
    private Integer[] arr = new Integer[2 ^ 22];

    /* Address of byte */
    private Output<Integer> dataAddressInput;

    /* Data to read */
    private Output<Integer> dataInput;

    Memory() {
        dataOutput = new Output<Integer>() {
            @Override
            public Integer read() {
                System.out.print("[");
                // Fetching 4 bytes at a time
                int address = dataAddressInput.read();
                int data = arr[address / 4];
                System.out.printf(" %d]", address);
                return data;
            }
        };
    }

    public Output<Integer> getInstructionOutput() {
        return instructionOutput;
    }

    public Output<Integer> getDataOutput() {
        return dataOutput;
    }

    public void init(Output<Integer> dataInput, Output<Integer> dataAddressInput) {
        this.dataInput = dataInput;
        this.dataAddressInput = dataAddressInput;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        // Fetching 2 bytes at a time
        int index = dataAddressInput.read() / 4;
        arr[index] = dataInput.read();
        System.out.printf("Store %d from %d %n", arr[index], index);
    }

    public void readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            arr[i] = Integer.parseInt(line);
            i++;
        }
    }
}
