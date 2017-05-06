/**
 * Store the microinstructions. Input is muxInput. Output is microinstruction.
 * Created by kzlou on 4/13/2017.
 */
public class Decoder {

    /** Contains the microinstruction to be executed */
    private Output<Boolean> wbEnableOutput;

    /** Index of ALU mux */
    private Output<Integer> aluMuxIndexOutput;

    /** Index of PC mux */
    private Output<Integer> pcMuxIndexOutput;

    /** Index of write back mux */
    private Output<Integer> wbMuxIndexOutput;

    /** True if data will be written to memory */
    private Output<Boolean> memoryWriteEnableOutput;

    /** List of opcode microinstructions */
    private MicroInstruction[] opcodeMicroInstructions = new MicroInstruction[64];

    /** List of funct microinstructions */
    private MicroInstruction[] functMicroInstructions = new MicroInstruction[64];

    /** Opcode in instruction */
    private Output<Byte> opcodeInput;

    /**
     * Type of operation in instruction
     */
    private Output<Byte> functInput;

    /** ALU operation in instruction */
    private Output<Byte> aluOpOutput;

    /** Index of write back selector */
    private Output<Integer> wbSelectorMuxIndexOutput;

    /**
     * Generate outputs for each of the fields in the microinstruction
     */
    Decoder() {
        for(int i = 0; i < 64; i++) {
            opcodeMicroInstructions[i] = new MicroInstruction();
            functMicroInstructions[i] = new MicroInstruction();
        }
        opcodeMicroInstructions[0].setWbEnable(false).setMemoryWriteEnable(false);
        opcodeMicroInstructions[35].setWbSelectorMuxIndex(1).setPcMuxIndex(1).setAluMuxIndex(1).setWbMuxIndex(0).setMemoryWriteEnable(false).setWbEnable(true);
        wbEnableOutput = () -> getMicroInstruction().isWbEnable();
        aluMuxIndexOutput = () -> getMicroInstruction().getAluMuxIndex();
        pcMuxIndexOutput = () -> getMicroInstruction().getPcMuxIndex();
        wbMuxIndexOutput = () -> getMicroInstruction().getWbMuxIndex();
        memoryWriteEnableOutput = () -> getMicroInstruction().isMemoryWriteEnable();
        aluOpOutput = () -> getMicroInstruction().getAluOp();
        wbSelectorMuxIndexOutput = () -> getMicroInstruction().getWbSelectorMuxIndex();
    }

    /**
     * Get microinstruction. If opcode is 0, it is a register instruction. Get microinstruction by using
     * funct as index to functMicroInstructions. Otherwise, use opcode as index to opcodeMicroInstructions.
     *
     * @return
     */
    public MicroInstruction getMicroInstruction() {
        if (opcodeInput.read() == 0) {
            return functMicroInstructions[functInput.read()];
        }
        return opcodeMicroInstructions[opcodeInput.read()];
    }

    /**
     * Getter for memory write enable
     *
     * @return memory write enable
     */
    public Output<Boolean> getMemoryWriteEnableOutput() {
        return memoryWriteEnableOutput;
    }

    /**
     * Getter for ALU operation
     *
     * @return ALU operation
     */
    public Output<Byte> getAluOpOutput() {
        return aluOpOutput;
    }

    public void init(Output<Byte> opcodeInput, Output<Byte> functInput) {
        this.opcodeInput = opcodeInput;
        this.functInput = functInput;
    }

    /**
     * Getter for write back enable
     *
     * @return write back enable
     */
    public Output<Boolean> getWbEnableOutput() {
        return wbEnableOutput;
    }

    /**
     * Getter for ALU mux index
     *
     * @return ALU mux index
     */
    public Output<Integer> getAluMuxIndexOutput() {
        return aluMuxIndexOutput;
    }

    /**
     * Getter for
     *
     * @return
     */
    public Output<Integer> getPcMuxIndexOutput() {
        return pcMuxIndexOutput;
    }

    /**
     * Getter for
     *
     * @return
     */
    public Output<Integer> getWbMuxIndexOutput() {
        return wbMuxIndexOutput;
    }

    /**
     * Getter for
     *
     * @return
     */
    public Output<Integer> getWbSelectorMuxIndexOutput() {
        return wbSelectorMuxIndexOutput;
    }
}
