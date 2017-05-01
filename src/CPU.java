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

    private InstructionDecode instructionDecode;

    private InstructionFetch instructionFetch;

    private RegisterBank registerBank;

    private Execute execute;

    private MemoryAccess memoryAccess;

    private WriteBack writeBack;

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
    }

    public void init() {
        execute
                .setSInput(instructionDecode.getSOutput())
                .setTInput(instructionDecode.getTOutput())
                .setCInput(instructionDecode.getCOutput())
                .setAluOpInput(instructionDecode.getAluOpOutput())
                .setWbSelectorInput(instructionDecode.getWbSelectorOutput())
                .setAluMuxIndexInput(instructionDecode.getAluMuxIndexOutput());
    }

    public void test() throws IOException {
        memory.readFile("memoryInput2.txt");
        run();
    }

    /**
     * Instructs the CPU. The instructions are provided by the micro instruction
     * @param microInstruction instructions
     */
    public void execute(MicroInstruction microInstruction) {
    }

    public void execute(MicroInstruction[] microInstructions) {
        for(int i = 0; i < microInstructions.length; i++) {
            execute(microInstructions[i]);
        }
    }

    /**
     * Fetches microinstruction and executes it
     */
    public void run() {
        // Output<MicroInstruction> microInstructionOutput = decoder.getWbEnableOutput();
        for(int i = 0; ; i++) {
            // MicroInstruction currentMicroInstruction = microInstructionOutput.read();
            // if(currentMicroInstruction.halt) {
                System.out.printf(" -> HALT%n");
                break;
            }
            // System.out.printf(" -> %s%n", currentMicroInstruction);
            // execute(currentMicroInstruction);
        }
    }
