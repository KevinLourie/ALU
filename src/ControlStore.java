/**
 * Store the microinstructions. Input is index. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class ControlStore {

    // Contains the microinstruction to be executed
    Output<MicroInstruction> microInstructionOutput;

    // List of microinstructions
    MicroInstruction[] microInstructions = new MicroInstruction[]{
            // MAR reads addressInput from program counter
            new MicroInstruction().setMarMuxIndex(1).setMarOp(RegisterOp.Store),

            // MBR reads instruction, program counter points to next program
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setPcOp(RegisterOp.Increment2),

            // IR reads instruction from MBR
            new MicroInstruction().setIrOp(RegisterOp.Store),

            // LDAx
            new MicroInstruction().setMpcOp(RegisterOp.Store),
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store),
            new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store)
    };

    /**
     * Constructor
     *
     * @param addressInput location of microinstruction to be executed
     */
    ControlStore(Output<Short> addressInput) {
        microInstructionOutput = new Output<MicroInstruction>() {

            @Override
            public MicroInstruction read() {
                System.out.print("[");
                Short address = addressInput.read();
                System.out.printf(" %d]", address);
                return microInstructions[address];
            }
        };
    }

    public Output<MicroInstruction> getMicroInstructionOutput() {
        return microInstructionOutput;
    }

}
