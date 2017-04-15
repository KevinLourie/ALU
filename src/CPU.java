import java.io.IOException;

/**
 * The CPU fetches the machine code, translates it into microcode, then executes the microinstructions
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /**
     * Memory buffer
     */
    private Register16 mbr = new Register16("MBR");

    /**
     * Control store
     */
    private ControlStore cs;

    /**
     * Data bus
     */
    private Bus<Short> dataBus = new Bus();

    /**
     * Reads or writes integers to buses
     */
    private Register16 ac = new Register16("AC");

    /**
     * Arithmetic logic unit
     */
    private ALU alu = new ALU();

    /**
     * Chooses which bus the accumulator will read from
     */
    private Multiplexer<Short> acMux = new Multiplexer();

    /**
     * Main memory
     */
    private Memory memory = new Memory();

    /**
     * Memory address register
     */
    private Register32 mar = new Register32("MAR");

    /**
     * Program counter
     */
    private Register32 pc = new Register32("PC");

    /**
     * Stack pointer
     */
    private Register32 sp = new Register32("SP");

    /**
     * Instruction register
     */
    private RegisterI ir = new RegisterI("IR");

    /**
     * Chooses input to MAR
     */
    private Multiplexer<Integer> marMux = new Multiplexer();

    /**
     * Chooses input to MBR
     */
    private Multiplexer<Short> mbrMux = new Multiplexer();

    /**
     * Decoder
     */
    private Decoder decoder;

    /**
     * Microprogram counter
     */
    private Register16 mpc = new Register16("MPC");

    CPU() {
        // Make the decoder
        decoder = new Decoder();

        // The ALU operates on the dataOutput read from mbr and ac
        alu.init(mbr, ac);

        // The IR will read from the MBR
        ir.init(mbr);

        // The decoder reads from the instruction register
        decoder.init(ir.getOpcodeOutput());

        // The MBR will either read from main memory or the ALU
        mbrMux.init(memory, alu);

        // Read either from main memory or ALU
        mbr.init(mbrMux);

        cs = new ControlStore(mbr);

        // The program counter reads the index of the current program from the instruction register
        pc.init(ir.getAddressOutput());

        // The microprogram counter reads the index of the current microprogram from the decoder
        mpc.init(decoder);

        // The stack pointer reads the index of next element in the stack from the instruction register
        sp.init(ir.getAddressOutput());

        // Pick input for memory addressOutput register
        marMux.init(ir.getAddressOutput(), pc, sp);

        // The input to MAR was chosen by the MAR multiplexer
        mar.init(marMux);

        // Create the main memory with dataOutput input of mbr and addressOutput input of mar
        memory.init(mbr, mar);

        // Choose the mbr or the alu
        acMux.init(mbr, alu);

        // The AC reads the dataOutput from the mbr or the alu
        ac.init(acMux);
    }

    public void test() throws IOException {
        memory.readFile("memoryInput.txt");
        MicroInstruction[] microInstructions = new MicroInstruction[]{
                new MicroInstruction().setMarMuxIndex(1).setMarOp(RegisterOp.Store),
                new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store).setPcOp(RegisterOp.Increment2),
                new MicroInstruction().setIrOp(RegisterOp.Store),
                new MicroInstruction().setMarMuxIndex(0).setMarOp(RegisterOp.Store),
                new MicroInstruction().setMbrMuxIndex(0).setMbrOp(RegisterOp.Store),
                new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store),
        };

        execute(microInstructions);
        System.out.printf(" %s%n", ac.read());
    }

    /**
     * Instructs the CPU. The instructions are provided by the micro instruction
     * @param microInstruction instructions
     */
    public void execute(MicroInstruction microInstruction) {
        acMux.write(microInstruction.acMuxIndex);
        marMux.write(microInstruction.marMuxIndex);
        mbrMux.write(microInstruction.mbrMuxIndex);

        mbr.write(microInstruction.mbrOp);
        ac.write(microInstruction.acOp);
        alu.write(microInstruction.aluOp);
        pc.write(microInstruction.pcOp);
        ir.write(microInstruction.irOp);
        mar.write(microInstruction.marOp);
        mpc.write(microInstruction.mpcOp);

        pc.cycle();
        mbr.cycle();
        ir.cycle();
        ac.cycle();
        mar.cycle();
        mpc.cycle();
    }

    public void execute(MicroInstruction[] microInstructions) {
        for(int i = 0; i < microInstructions.length; i++) {
            execute(microInstructions[i]);
        }
    }

    public void run() {
        Output<MicroInstruction> microInstructionOutput = cs.getMicroInstructionOutput();
        for(;;) {
            execute(microInstructionOutput.read());
        }
    }
}