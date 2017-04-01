import java.util.Scanner;

/**
 * Created by kzlou on 3/31/2017.
 */
public class CPU {

    /** First bus */
    private Bus<Integer> mbrBus;

    /** Second bus */
    private Bus<Integer> acBus;

    /** Third bus */
    private Bus<Integer> aluBus;

    /** Operator bus */
    private Bus<Operator> aluControl;

    private Bus<MuxOp> acMuxOp;

    private Multiplexer<Integer> acMux;

    private Register<Integer> ac;

    private Bus<RegisterOp> regOp;

    /** Arithmetic logic unit */
    private ALU alu;

    CPU() {
        mbrBus = new Bus();
        acBus = new Bus();
        aluBus = new Bus();
        aluControl = new Bus();
        acMuxOp = new Bus();

        // Choose the mbrBus or the aluBus
        acMux = new Multiplexer(acMuxOp, mbrBus, aluBus);
        regOp = new Bus();

        // The AC reads the data from the mbrBus or the aluBus. It writes to the acBus
        ac = new Register(acMux, acBus, regOp);

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

}
