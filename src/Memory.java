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

    /** Data in memory */
    private Output<Integer> dataOutput;

    /** Instruction in memory */
    private Output<Integer> instructionOutput;

    /** Temporary data */
    private int tempData;

    /** Temporary address */
    private int tempDataAddress;

    /* Byte array */
    private Integer[] arr = new Integer[2 ^ 22];

    /** Address of data */
    private Output<Integer> dataAddressInput;

    /** Address of instruction */
    private Output<Integer> instructionAddressInput;

    /* Data to read */
    private Output<Integer> dataInput;

    /** Controls whether data is written to memory */
    private Output<Boolean> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    Memory(Cycler cycler) {
        dataOutput = () -> {
            System.out.print("[");
            // Fetching 4 bytes at a time
            int address = dataAddressInput.read();
            int data = arr[address / 4];
            System.out.printf(" %d]", address);
            return data;
        };
        cycler.add(this);
    }

    public Output<Integer> getInstructionOutput() {
        return instructionOutput;
    }

    public Output<Integer> getDataOutput() {
        return dataOutput;
    }

    public void initData(Output<Integer> dataInput, Output<Integer> dataAddressInput, Output<Boolean> enableInput) {
        this.dataInput = dataInput;
        this.dataAddressInput = dataAddressInput;
        this.enableInput = enableInput;
    }

    public void initInstruction(Output<Integer> instructionAddressInput) {
        this.instructionAddressInput = instructionAddressInput;
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
