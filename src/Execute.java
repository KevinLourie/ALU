/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /** Required to do arithmetic for the instruction execution */
    private Alu alu;

    /** Indicates which register to write back to */
    private Register<Byte> wbSelectorLatch;

    /** Chooses either T or C */
    private Multiplexer<Integer> aluMux;

    /** Holds S register output */
    private Register<Integer> sLatch;

    /** Holds T register output */
    private Register<Integer> tLatch;

    /** Holds ALU operation */
    private Register<Byte> aluOpLatch;

    /** Holds constant from instruction */
    private Register<Integer> cLatch;

    /** Determines whether T or C is chosen */
    private Register<Integer> aluMuxIndexLatch;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new Alu();
        aluMux = new Multiplexer<>();
        aluMuxIndexLatch = new Register<>("Execute.aluMuxIndex", 0, cycler);
        wbSelectorLatch = new Register<>("Execte.wbSelector", (byte)0, cycler);
        sLatch = new Register<>("S", 0, cycler);
        tLatch = new Register<>("T", 0, cycler);
        cLatch = new Register<>("C", 0, cycler);
        aluOpLatch = new Register<>("Execute.aluOp", AluOp.Add, cycler);

        // Internal wire
        alu
                .setInput0(sLatch.getOutput())
                .setInput1(aluMux.getOutput())
                .setOperation(aluOpLatch.getOutput());
        aluMux
                .setIndexInput(aluMuxIndexLatch.getOutput())
                .setInputs(sLatch.getOutput(), tLatch.getOutput());
    }

    /**
     * Initialize sLatch
     * @param sInput input to sLatch
     * @return Execute
     */
    public Execute setSInput(Output<Integer> sInput) {
        // TODO: add correct enable inputs
        sLatch.setInput(sInput);
        return this;
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Execute
     */
    public Execute setTInput(Output<Integer> tInput) {
        // TODO: add correct enable inputs
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize cLatch
     * @param cInput input to cLatch
     * @return Execute
     */
    public Execute setCInput(Output<Integer> cInput) {
        // TODO: add correct enable inputs
        cLatch.setInput(cInput);
        return this;
    }

    /**
     * Initialize aluOpLatch
     * @param aluOpInput input to aluOpLatch
     * @return Execute
     */
    public Execute setAluOpInput(Output<Byte> aluOpInput) {
        aluOpLatch.setInput(aluOpInput);
        return this;
    }

    /**
     * Initialize wbSelectorLatch
     * @param wbSelectorInput input to wbSelectorLatch
     * @return Execute
     */
    public Execute setWbSelectorInput(Output<Byte> wbSelectorInput) {
        wbSelectorLatch.setInput(wbSelectorInput);
        return this;
    }

    /**
     * Initialize aluMux
     * @param muxIndexInput input to aluMux
     * @return Execute
     */
    public Execute setAluMuxIndexInput(Output<Integer> muxIndexInput) {
        aluMuxIndexLatch.setInput(muxIndexInput);
        return this;
    }

    public Output<Integer> getD1Output() {
        return alu.getOutput();
    }

    public Output<Integer> getD0Input() {
        return tLatch.getOutput();
    }

    public Output<Byte> getWBSelector() {
        return wbSelectorLatch.getOutput();
    }
}