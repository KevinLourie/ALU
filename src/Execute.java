/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /** Required to do arithmetic for the instruction execution */
    private Alu alu;

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

    private Register<Boolean> memoryWriteEnableLatch;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new Alu();
        aluMux = new Multiplexer<>();
        aluMuxIndexLatch = new Register<>("Execute.aluMuxIndex", 0, cycler);
        sLatch = new Register<>("Execute.S", 0, cycler);
        tLatch = new Register<>("Execute.T", 0, cycler);
        cLatch = new Register<>("Execute.C", 0, cycler);
        memoryWriteEnableLatch = new Register<>("Execute.Memory Write Enable", true, cycler);
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

    public Output<Integer> getD1Output() {
        return alu.getOutput();
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
     * Initialize aluMux
     * @param muxIndexInput input to aluMux
     * @return Execute
     */
    public Execute setAluMuxIndexInput(Output<Integer> muxIndexInput) {
        aluMuxIndexLatch.setInput(muxIndexInput);
        return this;
    }

    public Execute setMemoryWriteEnableInput(Output<Boolean> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Integer> getD0Output() {
        return tLatch.getOutput();
    }

    public Output<Boolean> getMemoryWriteEnableOutput() {
        return memoryWriteEnableLatch.getOutput();
    }
}