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

    private int[] arr = new int[2 ^ 22];

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

    WbLatches wbLatches0;

    WbLatches wbLatches1;

    WbLatches wbLatches2;

    CPU() {
        cycler = new Cycler();
        execute = new Execute(cycler);
        decoder = new Decoder();
        instructionFetch = new InstructionFetch(arr, cycler);
        instructionDecode = new InstructionDecode(cycler);
        memoryAccess = new MemoryAccess(arr, cycler);
        writeBack = new WriteBack(cycler);
        wbLatches0 = new WbLatches(cycler, "wbLatches0");
        wbLatches1 = new WbLatches(cycler, "wbLatches1");
        wbLatches2 = new WbLatches(cycler, "wbLatches2");

        // Internal Wiring
        wbLatches0
                .setWbEnableInput(instructionDecode.getWbEnableOutput())
                .setWbSelectorInput(instructionDecode.getWbSelectorMuxOutput())
                .setWbMuxIndexInput(instructionDecode.getWbMuxIndexOutput());
        wbLatches1.setLatchInputs(wbLatches0);
        wbLatches2.setLatchInputs(wbLatches1);
        instructionFetch
                .setNextPcInput(instructionDecode.getNextPcOutput())
                .setPcMuxIndexInput(instructionDecode.getPcMuxSelector());
        instructionDecode
                .setInstructionInput(instructionFetch.getInstructionOutput())
                .setWBSelectorInput(wbLatches2.getWbSelectorOutput())
                .setWbInput(writeBack.getWbOutput())
                .setWBEnableInput(wbLatches2.getWbEnableOutput())
                .setNextPcInput(instructionFetch.getNextPcOutput());
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getCOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setMemoryWriteEnableInput(instructionDecode.getMemoryWriteEnableOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput());
        memoryAccess
                .setMemoryWriteEnableInput(execute.getMemoryWriteEnableOutput())
                .setD0Input(execute.getD0Output())
                .setD1Input(execute.getD1Output());
        writeBack
                .setMuxIndexInput(wbLatches2.getWbMuxIndexOutput())
                .setWb0Input(memoryAccess.getWb0Output())
                .setWb1Input(memoryAccess.getWb1Output());
    }

    public void test() throws IOException {
        readFile("memoryInput.txt");
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
            System.out.printf("========== Cycle %d%n", i);
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
