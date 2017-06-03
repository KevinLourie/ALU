import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    WbLatches wbLatches0;
    WbLatches wbLatches1;
    WbLatches wbLatches2;

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
    /**
     * Fifth stage, where the data is either written to a register or to memory
     */
    private WriteBack writeBack;
    /**
     * Cycle all registers
     */
    private Cycler cycler;

    CPU() {
        cycler = new Cycler();
        execute = new Execute(cycler);
        instructionFetch = new InstructionFetch(memory, cycler);
        instructionDecode = new InstructionDecode(cycler, registers);
        memoryAccess = new MemoryAccess(memory, cycler);
        writeBack = new WriteBack(cycler);
        wbLatches0 = new WbLatches(cycler, "wbLatches0");
        wbLatches1 = new WbLatches(cycler, "wbLatches1");
        wbLatches2 = new WbLatches(cycler, "wbLatches2");

        // Internal Wiring
        wbLatches0
                .setWbEnableInput(instructionDecode.getWbEnableOutput())
                .setWbSelectorInput(instructionDecode.getWbSelectorMuxOutput())
                .setWbMuxIndexInput(instructionDecode.getWbMuxIndexOutput())
                .setHaltEnableLatch(instructionDecode.getHaltOutput());
        wbLatches1.setLatchInputs(wbLatches0);
        wbLatches2.setLatchInputs(wbLatches1);
        instructionFetch
                .setJumpAddressInput(instructionDecode.getNextPcOutput())
                .setJumpEnableInput(instructionDecode.getJumpEnable());
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
        Output<Boolean> halt = wbLatches2.getHaltEnableLatch();
        int i = 0;
        do {
            System.out.printf("========== Cycle %d%n", i);
            cycler.senseAndCycle();
            i++;
        }  while(!halt.read());

        for(int j = 0; j < registers.length; j++) {
            System.out.printf("R%d : %x%n", j, registers[j]);
        }
        for(int k = 0; k < 31; k++) {
            System.out.printf("%x : %x%n", 1024 + (4*k), memory[k+256]);
        }
    }

    private int[] convertToIntegerArray(String line) {
        String[] arr = line.split(",");
        int[] parts = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            parts[i] = Integer.parseUnsignedInt(arr[i], 16);
        }
        return parts;
    }

    public void readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int length;
        int result = 0;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.compareTo(".data") == 0) {
                i = 0x100;
                continue;
            }
            int[] parts = convertToIntegerArray(line);
            length = parts.length;
            switch (length) {
                case 1:
                    result = parts[0];
                    break;
                case 2:
                    result = (parts[0] << 26) + parts[1];
                    break;
                case 4:
                    result = (parts[0] << 26) + (parts[1] << 21) + (parts[2] << 16) + parts[3];
                    break;
                case 6:
                    result = (parts[0] << 26) + (parts[1] << 21) + (parts[2] << 16) + (parts[3] << 11) + (parts[4] << 6) + parts[5];
            }
            memory[i] = result;
            i++;
        }
    }
}
