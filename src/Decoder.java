/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    // Contains the microinstruction to be executed
    Output<MicroInstruction> microInstructionOutput;

    // List of microinstructions
    MicroInstruction[] microInstructions = new MicroInstruction[]{

    };

    Output<Byte> opcodeInput;

    /**
     * Constructor
     */
    Decoder() {
        microInstructionOutput = new Output<MicroInstruction>() {

            @Override
            public MicroInstruction read() {
                System.out.print("[");
                byte opcode = opcodeInput.read();
                System.out.printf(" %d]", opcode);
                return microInstructions[opcode];
            }
        };
    }

    public void init(Output<Byte> opcodeInput) {
        this.opcodeInput = opcodeInput;
    }

    public Output<MicroInstruction> getMicroInstructionOutput() {
        return microInstructionOutput;
    }

}
