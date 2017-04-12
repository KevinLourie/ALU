import java.io.IOException;

/**
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /**
     * Memory buffer
     */
    private Register16 mbr = new Register16("MBR");

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

    private Memory memory = new Memory();

    private Register32 mar = new Register32("MAR");

    private Register32 pc = new Register32("PC");

    private Register32 stackPointer = new Register32("SP");

    private RegisterI ir = new RegisterI("IR");

    private Multiplexer<Integer> marMux = new Multiplexer();

    private Multiplexer<Short> mbrMux = new Multiplexer();

    private Decoder decoder;

    private Register16 mpc = new Register16("SPC");

    CPU() {
        // The ALU operates on the data read from mbr and ac
        alu.init(mbr, ac);

        decoder.init(ir.getOpcodeOutput());


        mbrMux.init(memory, alu);

        // Read either from main memory or ALU
        mbr.init(mbrMux);


        ir.init(mbr);


        pc.init(ir.getAddressOutput());


        mpc.init(decoder);


        stackPointer.init(ir.getAddressOutput());

        // Pick input for memory addressOutput register
        marMux.init(ir.getAddressOutput(), pc, stackPointer);

        mar.init(marMux);

        // Create the main memory with data input of mbr and addressOutput input of mar
        memory.init(mbr, mar);

        // Choose the mbr or the alu
        acMux.init(mbr, alu);

        // The AC reads the data from the mbr or the alu
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
                new MicroInstruction().setAcMuxIndex(0).setAcOp(RegisterOp.Store)
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

        pc.cycle();
        mbr.cycle();
        ir.cycle();
        ac.cycle();
        mar.cycle();
    }

    public void execute(MicroInstruction[] microInstructions) {
        for(int i = 0; i < microInstructions.length; i++) {
            execute(microInstructions[i]);
        }
    }

}