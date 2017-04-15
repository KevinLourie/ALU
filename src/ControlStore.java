/**
 * Store the microinstructions. Input is index. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class ControlStore {

    // Contains the microinstruction to be executed
    Output<MicroInstruction> microInstructionOutput;

    // List of microinstructions
    MicroInstruction[] microInstructions = new MicroInstruction[]{
            // MAR reads address from program counter
            new MicroInstruction().setMarMuxIndex(1).setMarOp(RegisterOp.Store),
            // MBR reads instruction, program counter points to next program
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setPcOp(RegisterOp.Increment2),
            // IR reads instruction from MBR
            new MicroInstruction().setIrOp(RegisterOp.Store),
            new MicroInstruction().setMpcOp(RegisterOp.Store),
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store),
            new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store)
    };

    // Location of microinstruction to be executed
    Output<Short> address;

    ControlStore(Output<Short> address) {
        microInstructionOutput = new Output<MicroInstruction>() {

            @Override
            public MicroInstruction read() {
                System.out.println(microInstructions[address.read()]);
                return microInstructions[address.read()];
            }
        };
    }

    public Output<MicroInstruction> getMicroInstructionOutput() {
        return microInstructionOutput;
    }

}
