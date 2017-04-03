import java.util.Scanner;

/**
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /** Memory buffer */
    private Register<Integer> mbr;

    /** Memory buffer bus */
    private Bus<Integer> mbrBus;

    /** Chooses whether to read or write */
    private Bus<RegisterOp> mbrControl;

    /** Reads or writes integers to buses */
    private Register<Integer> ac;

    /** Accumulator bus */
    private Bus<Integer> acBus;

    /** Chooses whether to read or write */
    private Bus<RegisterOp> acControl;

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

    CPU() {
        // Create all data buses
        mbrBus = new Bus();
        acBus = new Bus();
        aluBus = new Bus();

        // Create all controls
        mbrControl = new Bus();
        acControl = new Bus();
        aluControl = new Bus();
        acMuxControl = new Bus();

        // Choose the mbrBus or the aluBus
        acMux = new Multiplexer(mbrBus, aluBus, acMuxControl);

        // The AC reads the data from the mbrBus or the aluBus. It writes to the acBus
        ac = new Register(acMux, acBus, acControl);

        // The ALU operates on the data read from mbrBus and acBus. It writes to the aluBus
        alu = new ALU(mbrBus, acBus, aluBus, aluControl);
    }

    public void test() {
        Operator operator = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first integer");
        mbrBus.write(scanner.nextInt());
        System.out.println("Enter second integer");
        acBus.write(scanner.nextInt());
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

    public void test2() {
        Operator operator = null;
        Scanner scanner = new Scanner(System.in);

        // Get the first number
        System.out.println("Enter first integer");

        // Write that number to the mbr bus
        mbrBus.write(scanner.nextInt());

        // Set multiplexer to left
        acMuxControl.write(MuxOp.Left);

        // Set accumulator to read
        acControl.write(RegisterOp.Read);

        // ac will read the ac mux. The ac will read from the mbr bus
        ac.cycle();

        // Get the second number
        System.out.println("Enter second integer");

        // Write that number to the mbr bus
        mbrBus.write(scanner.nextInt());

        // Get the operator
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

        // Write accumulator to ac bus
        acControl.write(RegisterOp.Write);
        ac.cycle();

        // Write operator to
        aluControl.write(operator);
        alu.cycle();
        System.out.println(aluBus.read());
    }

}
