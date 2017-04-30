/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /**
     * Required to do arithmetic for the instruction execution
     */
    private ALU alu;

    /**
     * Indicates which register to write back to
     */
    private Register<Byte> wbSelectorLatch;

    /**
     * Chooses either T or C
     */
    private Multiplexer<Integer> aluMux;

    /**
     * Holds S register output
     */
    private Register<Integer> sLatch;

    /**
     * Holds T register output
     */
    private Register<Integer> tLatch;

    /**
     * Holds ALU operation
     */
    private Register<AluOp> aluOpLatch;

    /**
     * Holds constant from instruction
     */
    private Register<Integer> cLatch;

    /**
     * Determines whether T or C is chosen
     */
    private Register<Integer> muxIndexLatch;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new ALU();
        aluMux = new Multiplexer<>();
        muxIndexLatch = new Register<>("Mux Index", 0, cycler);
        wbSelectorLatch = new Register<>("WB Selector", (byte)0, cycler);
        sLatch = new Register<>("S", 0, cycler);
        tLatch = new Register<>("T", 0, cycler);
        cLatch = new Register<>("C", 0, cycler);
        aluOpLatch = new Register<AluOp>("ALU Operator", AluOp.None, cycler);
    }

    /**
     * Initialize data registers, address register, and ALU operator register
     * @param sInput input to first data register
     * @param tInput input to second data register
     * @param cInput input to address register
     * @param aluOpInput input to ALU operator register
     */
    public void init(Output<Integer> sInput, Output<Integer> tInput, Output<Integer> cInput,
                     Output<AluOp> aluOpInput, Output<Byte> wbSelectorInput, Output<Integer> muxIndexInput) {
        // TODO: add correct enable inputs
        sLatch.init(sInput);
        tLatch.init(tInput);
        cLatch.init(cInput);
        wbSelectorLatch.init(wbSelectorInput);
        muxIndexLatch.init(muxIndexInput);
        aluOpLatch.init(aluOpInput);
        alu.init(sLatch.getOutput(), aluMux.getOutput(), aluOpLatch.getOutput());
        aluMux.init(muxIndexLatch.getOutput(), sLatch.getOutput(), tLatch.getOutput());
    }
}
