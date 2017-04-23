/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /**
     * Required to do arithmetic for the instruction execution
     */
    ALU alu;

    /**
     * Second operand
     */
    Multiplexer<Integer> aluMux;

    /**
     * First data register
     */
    Register<Integer> dataRegister1;

    /**
     * Second data register
     */
    Register<Integer> dataRegister2;

    /**
     * Address register
     */
    Register<Integer> addressRegister;

    /**
     * ALU Operator register
     */
    Register<AluOp> aluOpRegister;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute() {
        alu = new ALU();
        aluMux = new Multiplexer<>();
        dataRegister1 = new Register<>("Data Register 1", 0);
        dataRegister2 = new Register<>("Data Register 2", 0);
        addressRegister = new Register<>("Address Register", 0);
        aluOpRegister = new Register<AluOp>("ALU Operator", AluOp.None);
    }

    /**
     * Initialize data registers, address register, and ALU operator register
     * @param dataInput1 input to first data register
     * @param dataInput2 input to second data register
     * @param addressInput input to address register
     * @param aluOpInput input to ALU operator register
     */
    public void init(Output<Integer> dataInput1, Output<Integer> dataInput2, Output<Integer> addressInput, Output<AluOp> aluOpInput) {
        dataRegister1.init(dataInput1);
        dataRegister2.init(dataInput2);
        addressRegister.init(addressInput);
        aluOpRegister.init(aluOpInput);
        alu.init(dataRegister1.getOutput(), aluMux.getOutput(), aluOpRegister.getOutput());
        aluMux.init(addressInput);
    }

    /**
     * Cycle data registers and address register
     */
    public void cycle() {
        dataRegister1.cycle();
        dataRegister2.cycle();
        addressRegister.cycle();
    }

}
