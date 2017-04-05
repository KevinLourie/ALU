import java.util.logging.Logger;

/**
 * Created by kzlou on 4/3/2017.
 */
public class Memory implements Input<Integer> {

    byte[] arr = new byte[2^24];

    Input<Integer> address;

    Input<Integer> data;

    Input<MemoryOp> memoryControl;

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    Memory(Input<Integer> data, Input<Integer> address, Input<MemoryOp> memoryControl) {
        this.data = data;
        this.address = address;
        this.memoryControl = memoryControl;
    }

    public void cycle() {
        switch (memoryControl.read()) {
            case Store:
                int index = address.read();
                arr[index] = (byte)(int)data.read();
                logger.fine(String.format("Store %d from %d", arr[index], index));
                break;
            case None:
                break;
        }
    }

    @Override
    public Integer read() {
        int index = address.read();
        logger.fine(String.format("Read %d from %d", arr[index], index));
        return (int)arr[index];
    }
}
