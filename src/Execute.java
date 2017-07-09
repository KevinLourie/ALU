/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /** Required to do arithmetic for the instruction execution */
    private Alu alu;

    /** Chooses either T or C */
    private Multiplexer<Number32> aluMux;

    /** Holds S register output */
    private Register<Number32> sLatch;

    /** Holds T register output */
    private Register<Number32> tLatch;

    /** Holds ALU operation */
    private Register<Number8> aluOpLatch;

    /** Holds constant from instruction */
    private Register<Number32> cLatch;

    /** Determines whether T or C is chosen */
    private Register<Number8> aluMuxIndexLatch;

    private Register<Number8> memoryWriteEnableLatch;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new Alu();
        aluMux = new Multiplexer<>(2);
        aluMuxIndexLatch = new Register<>("Execute.aluMuxIndex", Number8.zero, cycler);
        sLatch = new Register<>("Execute.s", Number32.zero, cycler);
        tLatch = new Register<>("Execute.t", Number32.zero, cycler);
        cLatch = new Register<>("Execute.c", Number32.zero, cycler);
        memoryWriteEnableLatch = new Register<>("Execute.memoryWriteEnable", Number8.one, cycler);
        aluOpLatch = new Register<>("Execute.aluOp", new Number8(AluOp.Add, "Constant"), cycler);

        // Internal wire
        alu
                .setInput0(sLatch.getOutput())
                .setInput1(aluMux.getOutput())
                .setOperation(aluOpLatch.getOutput());
        aluMux
                .setIndexInput(aluMuxIndexLatch.getOutput())
                .setInput(0, tLatch.getOutput())
                .setInput(1, cLatch.getOutput());
    }

    /**
     * Initialize sLatch
     * @param sInput input to sLatch
     * @return Execute
     */
    public Execute setSInput(Output<Number32> sInput) {
        // TODO: add correct enable inputs
        sLatch.setInput(sInput);
        return this;
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Execute
     */
    public Execute setTInput(Output<Number32> tInput) {
        // TODO: add correct enable inputs
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize cLatch
     * @param cInput input to cLatch
     * @return Execute
     */
    public Execute setCInput(Output<Number32> cInput) {
        // TODO: add correct enable inputs
        cLatch.setInput(cInput);
        return this;
    }

    public Output<Number32> getResultOutput() {
        return alu.getOutput();
    }

    /**
     * Initialize aluOpLatch
     * @param aluOpInput input to aluOpLatch
     * @return Execute
     */
    public Execute setAluOpInput(Output<Number8> aluOpInput) {
        aluOpLatch.setInput(aluOpInput);
        return this;
    }

    /**
     * Initialize aluMux
     * @param muxIndexInput input to aluMux
     * @return Execute
     */
    public Execute setAluMuxIndexInput(Output<Number8> muxIndexInput) {
        aluMuxIndexLatch.setInput(muxIndexInput);
        return this;
    }

    public Execute setMemoryWriteEnableInput(Output<Number8> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Number32> getTOutput() {
        return tLatch.getOutput();
    }

    public Output<Number8> getMemoryWriteEnableOutput() {
        return memoryWriteEnableLatch.getOutput();
    }
}