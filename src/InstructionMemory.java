import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The memory contains dInput. You can read and write to the memory. The dataAddressInput comes from the MAR and the dInput comes
 * from the MBR. The dInput is retrieved using its dataAddressInput.
 * Created by kzlou on 4/3/2017.
 */
public class InstructionMemory {

    public final static Logger logger = Logger.getLogger(Register.class.getName());

    /** Instruction in memory */
    private Output<Integer> instructionOutput;

    /* Byte array */
    private int[] arr;

    /** Address of instruction */
    private Output<Integer> instructionAddressInput;

    InstructionMemory(int[] arr) {
        this.arr = arr;
        instructionOutput = () -> {
            // Fetching 4 bytes at a time
            int address = instructionAddressInput.read();
            int data = arr[address / 4];
            System.out.printf("InstructionMemory[%d] -> %d%n", address, data);
            return data;
        };
    }

    public Output<Integer> getInstructionOutput() {
        return instructionOutput;
    }

    public InstructionMemory setAddressInput(Output<Integer> instructionAddressInput) {
        this.instructionAddressInput = instructionAddressInput;
        return this;
    }
}
