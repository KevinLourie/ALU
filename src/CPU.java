import java.util.Scanner;

/**
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /**
     * Memory buffer
     */
    private Register<Short> mbr;

    /**
     * Data bus
     */
    private Bus<Integer> dataBus;

    /**
     * Reads or writes integers to buses
     */
    private Register<Short> ac;

    /**
     * Arithmetic logic unit
     */
    private ALU alu;

    /**
     * Chooses which bus the accumulator will read from
     */
    private Multiplexer<Integer> acMux;

    private Memory memory;

    private Bus<MemoryOp> memoryControl;

    private Register<Integer> mar;

    private Register<Integer> programCounter;

    private Register<Integer> stackPointer;

    private Register<Integer> irAddress;

    private Multiplexer<Integer> marMux;

    private Multiplexer<Short> mbrMux;

    CPU() {
        // Create the data bus
        dataBus = new Bus();

        // Create the control
        memoryControl = new Bus();

        // Create the memory buffer register
        mbr = new Register(dataBus);

        // Create the ir address
        irAddress = new Register(mbr);

        // Create the program counter
        programCounter = new Register(irAddress);

        // Create the stack pointer
        stackPointer = new Register(irAddress);

        // Pick input for memory address register
        marMux = new Multiplexer(mbr, irAddress, programCounter, stackPointer);

        // Create the memory address register
        mar = new Register(marMux);

        // The ALU operates on the data read from mbr and ac
        alu = new ALU(mbr, ac);

        // Create the main memory with data input of mbr and address input of mar
        memory = new Memory(mbr, mar);

        // Read either from main memory or ALU
        mbrMux = new Multiplexer(memory, alu);

        // Choose the mbr or the alu
        acMux = new Multiplexer(mbr, alu);

        // The AC reads the data from the mbr or the alu
        ac = new Register(acMux);
    }

    public void test() {
        ALUOp operator = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first integer");
        dataBus.write(scanner.nextInt());

        mbr.write(RegisterOp.Store);
        mbr.cycle();

        // Set ac multiplexer to mbr
        acMux.write(0);

        ac.write(RegisterOp.Store);
        ac.cycle();

        System.out.println("Enter second integer");
        dataBus.write(scanner.nextInt());
        mbr.cycle();

        System.out.println("Enter 1 to add, 2 to subtract, 3 to multiply, or 4 to divide");
        int operatorNumber = scanner.nextInt();
        switch (operatorNumber) {
            case 1:
                operator = ALUOp.Add;
                break;
            case 2:
                operator = ALUOp.Subtract;
                break;
            case 3:
                operator = ALUOp.Multiply;
                break;
            case 4:
                operator = ALUOp.Divide;
                break;
        }

        alu.write(operator);
        System.out.println(alu.read());
    }

}