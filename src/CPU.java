import java.util.Scanner;

/**
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /** Memory buffer */
    private Register<Integer> mbr;

    /** Data bus */
    private Bus<Integer> dataBus;

    /** Reads or writes integers to buses */
    private Register<Integer> ac;

    /** Arithmetic logic unit */
    private ALU alu;

    /** ALU bus */
    private Bus<Integer> aluBus;

    /** Operator bus */
    private Bus<Operator> aluControl;

    /** Control bus that tells multiplexer what to do */
    private Bus<MuxOp> acMuxControl;

    /** Chooses which bus the accumulator will read from */
    private Multiplexer<Integer> acMux;

    private MainMemory memory;

    private Bus<MemoryOp> memoryControl;

    private Register<Integer> mar;

    CPU() {
        // Create all data buses
        aluBus = new Bus();
        dataBus = new Bus();

        // Create all controls
        aluControl = new Bus();
        acMuxControl = new Bus();
        memoryControl = new Bus();

        mbr = new Register(dataBus);

        mar = new Register(null);

        memory = new MainMemory(mbr, mar, memoryControl);

        // Choose the mbrBus or the aluBus
        acMux = new Multiplexer(mbr, aluBus, acMuxControl);

        // The AC reads the data from the mbrBus or the aluBus. It writes to the acBus
        ac = new Register(acMux);

        // The ALU operates on the data read from mbrBus and acBus. It writes to the aluBus
        alu = new ALU(mbr, ac, aluBus, aluControl);
    }

    public void test() {
        Operator operator = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first integer");
        dataBus.write(scanner.nextInt());

        mbr.write(RegisterOp.Store);
        mbr.cycle();

        // Set ac multiplexer to mbr
        acMuxControl.write(MuxOp.Left);

        ac.write(RegisterOp.Store);
        ac.cycle();

        System.out.println("Enter second integer");
        dataBus.write(scanner.nextInt());
        mbr.cycle();

        System.out.println("Enter 1 to add, 2 to subtract, 3 to multiply, or 4 to divide");
        int operatorNumber = scanner.nextInt();
        switch (operatorNumber) {
            case 1:
                operator = Operator.Add;
                break;
            case 2:
                operator = Operator.Subtract;
                break;
            case 3:
                operator = Operator.Multiply;
                break;
            case 4:
                operator = Operator.Divide;
                break;
        }

        aluControl.write(operator);
        alu.cycle();
        System.out.println(aluBus.read());
    }

}