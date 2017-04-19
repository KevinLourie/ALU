/**
 * Store the microinstructions. Input is index. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class ControlStore {

    // Contains the microinstruction to be executed
    Output<MicroInstruction> microInstructionOutput;

    // List of microinstructions
    MicroInstruction[] microInstructions = new MicroInstruction[]{
            // 0: MAR reads addressInput from program counter
            new MicroInstruction().setMarMuxIndex(1).setMarOp(RegisterOp.Store),

            // 1: MBR reads instruction, program counter points to next program
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setPcOp(RegisterOp.Increment2),

            // 2: IR reads instruction from MBR
            new MicroInstruction().setIrOp(RegisterOp.Store),

            // 3: MBR reads instruction from the decoder
            new MicroInstruction().setMpcOp(RegisterOp.Store),

            // 4: TBD
            null,

            null,

            null,

            null,

            null,

            // HALT
            new MicroInstruction().setHalt(),

            // LDAx
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store),
            new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store).setMpcOp(RegisterOp.Clear),

            // STA x
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store).setMbrMuxIndex(1).setMbrOp(RegisterOp.Store).setAluOp(ALUOp.Right),
            new MicroInstruction().setMemoryOp(MemoryOp.Store),

            // ADD x
            new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
            new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setAluOp(ALUOp.Add),
            new MicroInstruction().setAcMuxIndex(1).setAcOp(RegisterOp.Store)

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
