import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dInput comes
 * from the MBR. The dInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class Memory implements ICycle {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Output<Integer> dataOutput;

    Output<Integer> instructionOutput;

    int tempData;

    int tempDataAddress;

    /* Byte array */
    private Integer[] arr = new Integer[2 ^ 22];

    /* Address of byte */
    private Output<Integer> dataAddressInput;

    /* Data to read */
    private Output<Integer> dataInput;

    private Output<Boolean> enableInput;

    private boolean tempEnable;

    Memory(Cycler cycler) {
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
        cycler.add(this);
    }

    public Output<Integer> getInstructionOutput() {
        return instructionOutput;
    }

    public Output<Integer> getDataOutput() {
        return dataOutput;
    }

    public void init(Output<Integer> dataInput, Output<Integer> dataAddressInput, Output<Boolean> enableInput) {
        this.dataInput = dataInput;
        this.dataAddressInput = dataAddressInput;
        this.enableInput = enableInput;
    }

    /**
     * Write data to memory
     */
    @Override
    public void cycle() {
        if(tempEnable) {
            // Fetching 2 bytes at a time
            int index = tempDataAddress / 4;
            arr[index] = tempData;
            System.out.printf("Store %d from %d %n", arr[index], index);
        }
    }

    @Override
    public void sense() {
        tempData = dataInput.read();
        tempDataAddress = dataAddressInput.read();
        tempEnable = enableInput.read();
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
