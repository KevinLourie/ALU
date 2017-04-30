/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    // Contains the microinstruction to be executed
    private Output<Boolean> wbEnableOutput;

    private Output<Integer> aluMuxIndexOutput;

    private Output<Integer> pcMuxIndexOutput;

    private Output<Integer> wbMuxIndexOutput;

    private Output<Boolean> memoryWriteEnableOutput;

    // List of microinstructions
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    private Output<Byte> opcodeInput;

    private Output<Byte> functInput;

    private Output<Byte> aluOpOutput;

    /**
     * Generate outputs for each of the fields in the microinstruction
     */
    Decoder() {
        opcodeMicroInstructions[0].setWbEnable(false).setMemoryWriteEnable(false);
        opcodeMicroInstructions[35] = new MicroInstruction().setPcMuxIndex(1).setAluMuxIndex(1).setWbMuxIndex(0).setMemoryWriteEnable(false).setWbEnable(true);
        wbEnableOutput = () -> getMicroInstruction().isWbEnable();
        aluMuxIndexOutput = () -> getMicroInstruction().getAluMuxIndex();
        pcMuxIndexOutput = () -> getMicroInstruction().getPcMuxIndex();
        wbMuxIndexOutput = () -> getMicroInstruction().getWbMuxIndex();
        memoryWriteEnableOutput = () -> getMicroInstruction().isMemoryWriteEnable();
        aluOpOutput = () -> getMicroInstruction().getAluOp();
    }

    private MicroInstruction getMicroInstruction() {
        if(opcodeInput.read() == 0) {
            return functMicroInstructions[functInput.read()];
        }
        return opcodeMicroInstructions[opcodeInput.read()];
    }

    public Output<Boolean> getMemoryWriteEnableOutput() {
        return memoryWriteEnableOutput;
    }

    public Output<Byte> getAluOpOutput() {
        return aluOpOutput;
    }

    public void init(Output<Byte> opcodeInput, Output<Byte> functInput) {
        this.opcodeInput = opcodeInput;
        this.functInput = functInput;
    }

    public Output<Boolean> getWbEnableOutput() {
        return wbEnableOutput;
    }

    public Output<Integer> getAluMuxIndexOutput() {
        return aluMuxIndexOutput;
    }

    public Output<Integer> getPcMuxIndexOutput() {
        return pcMuxIndexOutput;
    }

    public Output<Integer> getWbMuxIndexOutput() {
        return wbMuxIndexOutput;
    }


}
