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

    InstructionDecode instructionDecode;

    InstructionFetch instructionFetch;

    RegisterBank registerBank;

    Execute execute;

    MemoryAccess memoryAccess;

    WriteBack writeBack;

    CPU() {
       execute = new Execute();
       decoder = new Decoder();
       memory = new Memory();
       registerBank = new RegisterBank();
       instructionFetch = new InstructionFetch(memory);
       instructionDecode = new InstructionDecode(registerBank);
       memoryAccess = new MemoryAccess(memory);
       writeBack = new WriteBack();
    }

    public void init() {
        execute.init(registerBank.getsOutput(), registerBank.gettOutput(), instructionDecode.getAddressRegisterOutput(), null);

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
        Output<MicroInstruction> microInstructionOutput = decoder.getMicroInstructionOutput();
        for(int i = 0; ; i++) {
            MicroInstruction currentMicroInstruction = microInstructionOutput.read();
            if(currentMicroInstruction.halt) {
                System.out.printf(" -> HALT%n");
                break;
            }
            System.out.printf(" -> %s%n", currentMicroInstruction);
            execute(currentMicroInstruction);
        }
    }
}