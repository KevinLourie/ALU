import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /**
     * Control store
     */
    private Decoder decoder;

    /**
     * Main memory
     */
    private Memory memory;

    /**
     * Second stage, where the instruction is broken down into its part
     */
    private InstructionDecode instructionDecode;

    /**
     * First stage, where the instruction is fetched
     */
    private InstructionFetch instructionFetch;

    /**
     * Register bank
     */
    private RegisterBank registerBank;

    /**
     * Third stage, where the ALU performs an operation
     */
    private Execute execute;

    /**
     * Fourth stage, where the data from a register is stored in memory
     */
    private MemoryAccess memoryAccess;

    /**
     * Fifth stage, where the data is either writen to a register or to memory
     */
    private WriteBack writeBack;

    /**
     * Cycle all registers
     */
    private Cycler cycler;

    CPU() {
        cycler = new Cycler();
        execute = new Execute(cycler);
        decoder = new Decoder();
        memory = new Memory(cycler);
        registerBank = new RegisterBank(cycler);
        instructionFetch = new InstructionFetch(memory, cycler);
        instructionDecode = new InstructionDecode(registerBank, cycler);
        memoryAccess = new MemoryAccess(memory, cycler);
        writeBack = new WriteBack(cycler, registerBank);

        // Internal Wiring
        instructionFetch
                .setDataInput(memory.getInstructionOutput())
                .setNextPCInput(memory.getInstructionOutput())
                .setPcMuxIndexInput(decoder.getPcMuxIndexOutput());
        instructionDecode
                .setInstructionInput(instructionFetch.getInstructionOutput())
                .setNextPcInput(memory.getInstructionOutput());
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getCOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setWbSelectorInput(instructionDecode.getWbSelectorOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput());
        memoryAccess
                .setIndexInput(decoder.getWbSelectorMuxIndexOutput())
                .setD0Input(memory.getDataOutput())
                .setD1Input(decoder.getAluMuxIndexOutput())
                .setdAddressInput(instructionDecode.getWbSelectorOutput())
                .setEnableOutput(decoder.getWbEnableOutput());
        writeBack
                .setWbEnableInput(memoryAccess.getWbEnableOutput())
                .setWbInputs(memoryAccess.getWb0Output(), memoryAccess.getWb1Output())
                .setWbMuxIndexInput(memoryAccess.getDIndexOutput())
                .setWbSelectorInput(execute.getWBSelector());
    }

    public void test() throws IOException {
        memory.readFile("memoryInput3.txt");
        run();
    }

    /**
     * Instructs the CPU. The instructions are provided by the micro instruction
     *
     * @param microInstruction instructions
     */
    public void execute(MicroInstruction microInstruction) {
    }

    public void execute(MicroInstruction[] microInstructions) {
        for (int i = 0; i < microInstructions.length; i++) {
            execute(microInstructions[i]);
        }
    }

    /**
     * Fetches microinstruction and executes it
     */
    public void run() {
        for (int i = 0; i < 5; i++) {
            cycler.senseAndCycle();
        }
    }
}
