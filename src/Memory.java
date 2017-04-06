import java.util.logging.Logger;

/**
 * Created by kzlou on 4/3/2017.
 */
public class Memory implements Input<Short>, Output<MemoryOp> {

    // Byte array
    Short[] arr = new Short[2^23];

    // Address of byte
    Input<Integer> address;

    // Data to read
    Input<Short> data;

    // Store, increment, or decrement
    MemoryOp memoryControl;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Memory(Input<Short> data, Input<Integer> address) {
        this.data = data;
        this.address = address;
    }

    public void cycle() {
        switch (memoryControl) {
            case Store:
                // Fetching 2 bytes at a time
                int index = address.read()/2;
                arr[index] = data.read();
                logger.fine(String.format("Store %d from %d", arr[index], index));
                break;
            case None:
                break;
        }
    }

    @Override
    public Short read() {
        // Fetching 2 bytes at a time
        int index = address.read()/2;
        logger.fine(String.format("Read %d from %d", arr[index], index));
        return arr[index];
    }

    @Override
    public void write(MemoryOp data) {
        memoryControl = data;
    }
}
