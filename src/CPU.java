import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /** Control store */
    private Decoder decoder;

    private Integer[] arr = new Integer[2 ^ 22];

    /** Second stage, where the instruction is broken down into its part */
    private InstructionDecode instructionDecode;

    /** First stage, where the instruction is fetched */
    private InstructionFetch instructionFetch;

    /** Third stage, where the ALU performs an operation */
    private Execute execute;

    /** Fourth stage, where the data from a register is stored in memory */
    private MemoryAccess memoryAccess;

    /** Fifth stage, where the data is either written to a register or to memory
     */
    private WriteBack writeBack;

    /** Cycle all registers */
    private Cycler cycler;

    WbLatches executeWbLatches;

    WbLatches memoryAccessLatches;

    WbLatches writeBackWbLatches;

    CPU() {
        cycler = new Cycler();
        execute = new Execute(cycler);
        decoder = new Decoder();
        instructionFetch = new InstructionFetch(arr, cycler);
        instructionDecode = new InstructionDecode(cycler);
        memoryAccess = new MemoryAccess(arr, cycler);
        writeBack = new WriteBack(cycler);
        writeBackWbLatches = new WbLatches(cycler);

        // Internal Wiring
        instructionFetch
                .setNextPcInput(instructionDecode.getNextPcOutput())
                .setPcMuxIndexInput(instructionFetch.getPcMuxOutput());
        instructionDecode
                .setInstructionInput(instructionFetch.getInstructionOutput())
                .setNextPcInput(instructionFetch.getPcMuxOutput())
                .setWBEnableInput(writeBack.getWbEnableLatch())
                .setWBInput(instructionDecode.getWbMuxIndexOutput())
                .setWBSelectorInput(writeBack.getWbSelectorOutput());
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getCOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setMemoryWriteEnableInput(instructionDecode.getMemoryWriteEnableOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput());
        memoryAccess
                .setMemoryWriteEnableInput(instructionDecode.getMemoryWriteEnableOutput())
                .setD0Input(instructionDecode.getTOutput())
                .setD1Input(decoder.getAluMuxIndexOutput())
                .setdAddressInput(instructionFetch.getInstructionOutput());
        writeBack
                .setWbControlInputs(writeBackWbLatches)
                .setWbDataInputs(memoryAccess.getWb0Output(), memoryAccess.getWb1Output());
    }

    public void test() throws IOException {
        readFile("memoryInput3.txt");
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

    public void readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            arr[i] = Integer.parseUnsignedInt(line, 16);
            i++;
        }
    }
}
