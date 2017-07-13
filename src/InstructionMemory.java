import java.util.logging.Logger;

/**
 * The memory contains dInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dInput comes
 * from the MBR. The dInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class InstructionMemory {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    /** Instruction in memory */
    private Output<Value32> instructionOutput;

    /* Number8 array */
    private int[] arr;

    /** Address of instruction */
    private Output<Value32> instructionAddressInput;

    InstructionMemory(int[] arr) {
        this.arr = arr;
        instructionOutput = () -> {
            // Fetching 4 bytes at a time
            Value32 address = instructionAddressInput.read();
            int data = arr[address.intValue() / 4];
            return new Value32(data, String.format("InstructionMemory(%s)", address));
        };
    }

    public Output<Value32> getInstructionOutput() {
        return instructionOutput;
    }

    public InstructionMemory setAddressInput(Output<Value32> instructionAddressInput) {
        this.instructionAddressInput = instructionAddressInput;
        return this;
    }
}
