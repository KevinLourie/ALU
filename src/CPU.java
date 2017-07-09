import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    WbControlUnit wbControlUnit;

    Loader loader;

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
                .setJumpEnableInput(instructionDecode.getJumpEnable());
        instructionDecode
                .setInstructionInput(instructionFetch.getInstructionOutput())
                .setWBSelectorInput(wbControlUnit.getWbSelectorOutput())
                .setWbInput(memoryAccess.getWbOutput())
                .setWBEnableInput(wbControlUnit.getWbEnableOutput())
                .setResultInput(execute.getResultOutput())
                .setSMuxIndex(wbControlUnit.getSMuxIndexOutput())
                .setTMuxIndex(wbControlUnit.getTMuxIndexOutput())
                .setNextPcInput(instructionFetch.getNextPcOutput())
                // TODO: set go input properly
                .setGoInput(wbControlUnit.getGoOutput());
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getImmediateOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setMemoryWriteEnableInput(instructionDecode.getMemoryWriteEnableOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput());
        memoryAccess
                .setMemoryWriteEnableInput(execute.getMemoryWriteEnableOutput())
                .setTInput(execute.getTOutput())
                .setResultInput(execute.getResultOutput())
                .setWbIndexInput(wbControlUnit.getWbMuxIndexOutput());
    }

    public void test() throws IOException {
        loader.readFile("memoryInput.txt", 1);
        loader.readFile("memoryInput.txt", 2);
        run();
    }

    /**
     * Fetches microinstruction and executes it
     */
    public void run() {
        Output<Number8> halt = wbControlUnit.getHaltEnableLatch();
        int i = 0;
        Number8 isHalt;
        do {
            System.out.printf("%n========== Cycle %d%n", i);
            cycler.senseAndCycle();
            i++;
            isHalt = halt.read();
            System.out.printf("-> check halt %s%n", isHalt);
            // TODO: remove check
            if (i > 300)
                break;
        }  while(isHalt.byteValue() ==0);

        for(int j = 0; j < registers.length; j++) {
            System.out.printf("R%d : %x%n", j, registers[j]);
        }
        for(int k = 0; k < 31; k++) {
            System.out.printf("%x : %x%n", 1024 + (4*k), memory[k+256]);
        }
        System.out.printf("Completed in %d cycles%n", i);
    }
}
