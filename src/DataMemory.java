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
    private Output<Number32> dataOutput;

    /** Temporary data */
    private Number32 tempData;

    /** Temporary address */
    private Number32 tempDataAddress;

    /* Word array */
    private int[] arr;

    /** Address of data */
    private Output<Number32> dataAddressInput;

    /* Data to read */
    private Output<Number32> dataInput;

    /** Controls whether data is written to memory */
    private Output<Number8> enableInput;

    /** Temporary enable */
    private Number8 tempEnable;

    DataMemory(Cycler cycler, int[] arr) {
        this.arr = arr;
        dataOutput = () -> {
            // Fetching 4 bytes at a time
            Number32 address = dataAddressInput.read();
            int data = arr[address.intValue() / 4];
            return new Number32(data, String.format("DataMemory(%s)", address));
        };
        cycler.add(this);
    }

    public Output<Number32> getDataOutput() {
        return dataOutput;
    }

    public DataMemory setDataInput(Output<Number32> dataInput) {
        this.dataInput = dataInput;
        return this;
    }

    public DataMemory setDataAddressInput(Output<Number32> dataAddressInput) {
        this.dataAddressInput = dataAddressInput;
        return this;
    }

    public DataMemory setEnableInput(Output<Number8> enableInput) {
        this.enableInput = enableInput;
        return this;
    }

    /**
     * Write data to memory
     */
    @Override
    public void cycle() {
        if(tempEnable.byteValue() != 0) {
            // Fetching 2 bytes at a time
            arr[tempDataAddress.intValue() / 4] = tempData.intValue();
        }
    }

    @Override
    public void sense() {
        tempEnable = enableInput.read();
        System.out.print("DataMemory");
        if(tempEnable.booleanValue()) {
            tempData = dataInput.read();
            tempDataAddress = dataAddressInput.read();
            System.out.printf("(%s) write %s", tempDataAddress, tempData);
        } else {
            tempData = null;
            tempDataAddress = null;
        }
        System.out.printf(" enable(%s)%n", tempEnable);
    }
}
