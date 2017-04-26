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
    Register<Integer> sRegister;

    /**
     * Second data register
     */
    Register<Integer> tRegister;

    /**
     * Address register
     */
    Register<Integer> nextPCRegister;

    /**
     * ALU Operator register
     */
    Register<AluOp> aluOpRegister;

    Register<Integer> constantRegister;

    Bus<Integer, Integer> shiftLeft;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new ALU();
        aluMux = new Multiplexer<>();
        sRegister = new Register<>("S", 0, cycler);
        tRegister = new Register<>("T", 0, cycler);
        nextPCRegister = new Register<>("Address", 0, cycler);
        aluOpRegister = new Register<AluOp>("ALU Operator", AluOp.None, cycler);
        shiftLeft = new Bus<Integer, Integer>() {
            @Override
            public Integer read() {
                return input.read() << 2;
            }
        };
    }

    /**
     * Initialize data registers, address register, and ALU operator register
     * @param sInput input to first data register
     * @param tInput input to second data register
     * @param addressInput input to address register
     * @param aluOpInput input to ALU operator register
     */
    public void init(Output<Integer> sInput, Output<Integer> tInput, Output<Integer> addressInput, Output<AluOp> aluOpInput) {
        // TODO: add correct enable inputs
        sRegister.init(sInput, null);
        tRegister.init(tInput, null);
        nextPCRegister.init(addressInput, null);
        aluOpRegister.init(aluOpInput, null);
        alu.init(sRegister.getOutput(), aluMux.getOutput(), aluOpRegister.getOutput());
        aluMux.init(addressInput);
    }
}
