import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dInput comes
 * from the MBR. The dInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class DataMemory implements ICycle {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    /** Data in memory */
    private Output<Integer> dataOutput;

    /** Temporary data */
    private int tempData;

    /** Temporary address */
    private int tempDataAddress;

    /* Word array */
    private int[] arr;

    /** Address of data */
    private Output<Integer> dataAddressInput;

    /* Data to read */
    private Output<Integer> dataInput;

    /** Controls whether data is written to memory */
    private Output<Byte> enableInput;

    /** Temporary enable */
    private boolean tempEnable;

    DataMemory(Cycler cycler, int[] arr) {
        this.arr = arr;
        dataOutput = () -> {
            // Fetching 4 bytes at a time
            int address = dataAddressInput.read();
            int data = arr[address / 4];
            System.out.printf("DataMemory[%d] -> ", address);
            return data;
        };
        cycler.add(this);
    }

    public Output<Integer> getDataOutput() {
        return dataOutput;
    }

    public DataMemory setDataInput(Output<Integer> dataInput) {
        this.dataInput = dataInput;
        return this;
    }

    public DataMemory setDataAddressInput(Output<Integer> dataAddressInput) {
        this.dataAddressInput = dataAddressInput;
        return this;
    }

    public DataMemory setEnableInput(Output<Byte> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Write data to memory
     */
    @Override
    public void cycle() {
        if(tempEnable) {
            // Fetching 2 bytes at a time
            arr[tempDataAddress / 4] = tempData;
        }
    }

    @Override
    public void sense() {
        tempEnable = enableInput.read() != 0;
        if(tempEnable) {
            tempData = dataInput.read();
            tempDataAddress = dataAddressInput.read();
            System.out.printf("DataMemory[%d] #%x from %s%n", tempDataAddress, tempData, dataInput);
        } else {
            tempData = 0;
            tempDataAddress = -1;
            System.out.println("DataMemory write disabled");
        }
    }
}
