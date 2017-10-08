import java.util.logging.Logger;

/**
 * The memory contains dInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dInput comes
 * from the MBR. The dInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class DataMemory implements ICycle {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    /** Data in memory */
    private Output<Value32> dataOutput;

    /** Temporary data */
    private Value32 tempData;

    /** Temporary address */
    private Value32 tempDataAddress;

    /* Word array */
    private int[] arr;

    /** Address of data */
    private Output<Value32> dataAddressInput;

    /* Data to read */
    private Output<Value32> dataInput;

    /** Controls whether data is written to memory */
    private Output<Value8> enableInput;

    /** Temporary enable */
    private Value8 tempEnable;

    DataMemory(Cycler cycler, int[] arr) {
        this.arr = arr;
        dataOutput = () -> {
            // Fetching 4 bytes at a time
            Value32 address = dataAddressInput.read();
            int data = arr[address.intValue() / 4];
            return new Value32(data);
        };
        cycler.add(this);
    }

    public Output<Value32> getDataOutput() {
        return dataOutput;
    }

    public DataMemory setDataInput(Output<Value32> dataInput) {
        this.dataInput = dataInput;
        return this;
    }

    public DataMemory setDataAddressInput(Output<Value32> dataAddressInput) {
        this.dataAddressInput = dataAddressInput;
        return this;
    }

    public DataMemory setEnableInput(Output<Value8> enableInput) {
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
        if(tempEnable.booleanValue()) {
            tempData = dataInput.read();
            tempDataAddress = dataAddressInput.read();
        } else {
            tempData = null;
            tempDataAddress = null;
        }
    }
}
