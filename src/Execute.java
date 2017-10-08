/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    /** Required to do arithmetic for the instruction execution */
    private Alu alu;

    /** Chooses either T or C */
    private Multiplexer<Value32> aluMux;

    /** Holds S register output */
    private Register<Value32> sLatch;

    /** Holds T register output */
    private Register<Value32> tLatch;

    /** Holds ALU operation */
    private Register<Value8> aluOpLatch;

    /** Holds constant from instruction */
    private Register<Value32> cLatch;

    /** Determines whether T or C is chosen */
    private Register<Value8> aluMuxIndexLatch;

    private Register<Value8> memoryWriteEnableLatch;

    /**
     * Construct ALU, ALU multiplexer, data registers, address register, and ALU operator register
     */
    Execute(Cycler cycler) {
        alu = new Alu();
        aluMux = new Multiplexer<>(2);
        aluMuxIndexLatch = new Register<>("Execute.aluMuxIndex", Value8.zero, cycler);
        sLatch = new Register<>("Execute.s", Value32.zero, cycler);
        tLatch = new Register<>("Execute.t", Value32.zero, cycler);
        cLatch = new Register<>("Execute.c", Value32.zero, cycler);
        memoryWriteEnableLatch = new Register<>("Execute.memoryWriteEnable", Value8.one, cycler);
        aluOpLatch = new Register<>("Execute.aluOp", new Value8(AluOp.Add), cycler);

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

    public String toStringDelta() {
        Joiner j = new Joiner(" ", "Execute(", ")");
        j.add(sLatch.toStringDelta());
        j.add(tLatch.toStringDelta());
        j.add(aluOpLatch.toStringDelta());
        j.add(cLatch.toStringDelta());
        j.add(memoryWriteEnableLatch.toStringDelta());
        j.add(aluMuxIndexLatch.toStringDelta());
        return j.toString();
    }

    /**
     * Initialize sLatch
     * @param sInput input to sLatch
     * @return Execute
     */
    public Execute setSInput(Output<Value32> sInput) {
        // TODO: add correct enable precomputedOutput
        sLatch.setInput(sInput);
        return this;
    }

    /**
     * Initialize tLatch
     * @param tInput input to tLatch
     * @return Execute
     */
    public Execute setTInput(Output<Value32> tInput) {
        // TODO: add correct enable precomputedOutput
        tLatch.setInput(tInput);
        return this;
    }

    /**
     * Initialize cLatch
     * @param cInput input to cLatch
     * @return Execute
     */
    public Execute setCInput(Output<Value32> cInput) {
        // TODO: add correct enable precomputedOutput
        cLatch.setInput(cInput);
        return this;
    }

    public Output<Value32> getResultOutput() {
        return alu.getOutput();
    }

    /**
     * Initialize aluOpLatch
     * @param aluOpInput input to aluOpLatch
     * @return Execute
     */
    public Execute setAluOpInput(Output<Value8> aluOpInput) {
        aluOpLatch.setInput(aluOpInput);
        return this;
    }

    /**
     * Initialize aluMux
     * @param muxIndexInput input to aluMux
     * @return Execute
     */
    public Execute setAluMuxIndexInput(Output<Value8> muxIndexInput) {
        aluMuxIndexLatch.setInput(muxIndexInput);
        return this;
    }

    public Execute setMemoryWriteEnableInput(Output<Value8> memoryWriteEnableInput) {
        memoryWriteEnableLatch.setInput(memoryWriteEnableInput);
        return this;
    }

    public Output<Value32> getTOutput() {
        return tLatch.getOutput();
    }

    public Output<Value8> getMemoryWriteEnableOutput() {
        return memoryWriteEnableLatch.getOutput();
    }
}