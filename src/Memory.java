import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dataOutput. You can read and write to the memory. The address comes from the MAR and the dataOutput comes
 * from the MBR. The dataOutput is retrieved using its address.
 * Created by kzlou on 4/3/2017.
 */
public class Memory implements Output<Short>, Input<MemoryOp> {

    // Byte array
    Short[] arr = new Short[2^23];

    // Address of byte
    Output<Integer> address;

    // Data to read
    Output<Short> dataOutput;

    // Store, increment, or decrement
    MemoryOp memoryControl;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    public void init(Output<Short> data, Output<Integer> address) {
        this.dataOutput = data;
        this.address = address;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        switch (memoryControl) {
            case Store:
                // Fetching 2 bytes at a time
                int index = address.read()/2;
                arr[index] = dataOutput.read();
                logger.fine(String.format("Store %d from %d", arr[index], index));
                break;
            case None:
                break;
        }
    }

    /**
     * Gets the dataOutput at the address
     * @return dataOutput at the address
     */
    @Override
    public Short read() {
        System.out.print("[");
        // Fetching 2 bytes at a time
        int address = this.address.read();
        Short data = arr[address/2];
        System.out.printf(" %d]", address);
        return data;
    }

    /**
     * Choose the memory operation
     * @param data memory operation
     */
    @Override
    public void write(MemoryOp data) {
        memoryControl = data;
    }

    public void readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int i = 0;
        while((line = bufferedReader.readLine()) != null) {
            arr[i] = Short.parseShort(line, 16);
            i++;
        }
    }
}
