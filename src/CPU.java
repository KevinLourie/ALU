import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    WbControlUnit wbControlUnit;

    Loader loader;

    /** Collection of registers */
    private int[] registers = new int[32];

    private int[] memory = new int[1 << 22];
    /**
     * Second stage, where the instruction is broken down into its part
     */
    private InstructionDecode instructionDecode;
    /**
     * First stage, where the instruction is fetched
     */
    private InstructionFetch instructionFetch;
    /**
     * Third stage, where the ALU performs an operation
     */
    private Execute execute;
    /**
     * Fourth stage, where the data from a register is stored in memory
     */
    private MemoryAccess memoryAccess;

    private Coprocessor coprocessor;

    /**
     * Cycle all registers
     */
    private Cycler cycler;

    private int cycle = 0;

    CPU() {
        cycler = new Cycler();
        coprocessor = new Coprocessor(cycler);
        loader = new Loader("memoryInput.txt", memory);
        execute = new Execute(cycler);
        instructionFetch = new InstructionFetch(memory, cycler);
        instructionDecode = new InstructionDecode(cycler, registers);
        memoryAccess = new MemoryAccess(memory, cycler);
        wbControlUnit = new WbControlUnit(cycler);

        // Internal Wiring
        coprocessor
                .setSLatchInput(instructionDecode.getSOutput())
                .setTLatchInput(instructionDecode.getTOutput())
                .setCoprocessorOpInput(instructionDecode.getAluOpOutput());
        wbControlUnit
                .setWbEnableInput(instructionDecode.getWbEnableOutput())
                .setWbSelectorInput(instructionDecode.getWbSelectorMuxOutput())
                .setWbMuxIndexInput(instructionDecode.getWbMuxIndexOutput())
                .setSSelectorInput(instructionDecode.getSSelectorOutput())
                .setTSelectorInput(instructionDecode.getTSelectorOutput())
                .setHaltEnableLatch(instructionDecode.getHaltOutput());
        instructionFetch
                .setJumpAddressInput(instructionDecode.getNextPcOutput())
                .setJumpEnableInput(instructionDecode.getJumpEnable())
                .setGo(wbControlUnit.getGoOutput());
        instructionDecode
                .setInstructionInput(instructionFetch.getInstructionOutput())
                .setWBSelectorInput(wbControlUnit.getWbSelectorOutput())
                .setWbInput(memoryAccess.getWbOutput())
                .setWBEnableInput(wbControlUnit.getWbEnableOutput())
                .setResultInput(execute.getResultOutput())
                .setSMuxIndex(wbControlUnit.getSMuxIndexOutput())
                .setTMuxIndex(wbControlUnit.getTMuxIndexOutput())
                .setNextPcInput(instructionFetch.getNextPcOutput())
                .setGo(wbControlUnit.getGoOutput());
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getImmediateOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setMemoryWriteEnableInput(instructionDecode.getMemoryWriteEnableOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput())
                .setGo(wbControlUnit.getGoOutput());
        memoryAccess
                .setMemoryWriteEnableInput(execute.getMemoryWriteEnableOutput())
                .setTInput(execute.getTOutput())
                .setResultInput(execute.getResultOutput())
                .setWbIndexInput(wbControlUnit.getWbMuxIndexOutput());
    }

    public void test() throws IOException {
        loader.readFile("memoryInput.txt", 1);
        loader.readFile("memoryInput.txt", 2);
        //TODO: Put in method to print out instructions
        printInstructions();
        run();
    }

    public void printInstructions() {
        for(int i = 0; i < 10; i++) {
            System.out.printf("%x : %x%n", 4*i, memory[i]);
        }
    }

    /**
     * Fetches microinstruction and executes it
     */
    public void run() {
        Output<Value8> halt = wbControlUnit.getHaltEnableLatch();
        Value8 isHalt;
        do {
            System.out.printf("%n========== Cycle %d%n", cycle);
            cycler.sense();
            toStringDelta();
            cycler.cycle();
            cycle++;
            isHalt = halt.read();
            System.out.printf("CheckHalt %s%n", isHalt);
        }  while(!isHalt.booleanValue() && cycle < 85);

        for(int k = 0; k < 31; k++) {
            System.out.printf("%x : %x%n", 1024 + (4*k), memory[k+256]);
        }
        System.out.printf("Completed in %d cycles%n", cycle);
    }

    public void toStringDelta() {
        String instructionFetchString = instructionFetch.toStringDelta();
        if(instructionFetchString != null) {
            System.out.println(instructionFetchString);
        }
        String instructionDecodeString = instructionDecode.toStringDelta();
        if(instructionDecodeString != null) {
            System.out.println(instructionDecodeString);
        }
        String executeString = execute.toStringDelta();
        if(executeString != null) {
            System.out.println(executeString);
        }
        String memoryAccessString = memoryAccess.toStringDelta();
        if(memoryAccessString != null) {
            System.out.println(memoryAccessString);
        }
        String wbControlUnitString = wbControlUnit.toStringDelta();
        if(wbControlUnitString != null) {
            System.out.println(wbControlUnitString);
        }
    }
}
