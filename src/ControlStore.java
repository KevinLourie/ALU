/**
 * Store the microinstructions. Input is index. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class ControlStore {

    Output<MicroInstruction> microInstructionOutput;

    MicroInstruction[] microInstructions = new MicroInstruction[]{
            new MicroInstruction().setMarMuxIndex(1).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setPcOp(RegisterOp.Increment2),
            new MicroInstruction().setIrOp(RegisterOp.Store),
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store),
            new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store)
    };

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

}
