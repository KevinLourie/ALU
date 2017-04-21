import java.util.logging.Logger;

/**
 * Created by kzlou on 4/21/2017.
 */
public class RegisterBank {

    // Register array
    Integer[] arr = new Integer[32];

    // Address of register
    byte address;

    // Data to read
    int data;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    public void init(int data, byte address) {
        this.data = data;
        this.address = address;
    }

    /**
     * Write data to memory
     */
    public void cycle() {
        // Fetching 2 bytes at a time
        int index = address / 2;
        arr[index] = data;
        logger.fine(String.format("Store %d from %d", arr[index], index));
    }

    /**
     * Gets the data at the address
     *
     * @return data at the address
     */
    public int read() {
        System.out.print("[");
        // Fetching 2 bytes at a time
        int address = this.address;
        int data = arr[address / 2];
        System.out.printf(" %d]", address);
        return data;
    }
}
