/**
 * Stores operations for all parts of the CPU
 * Created by kzlou on 4/6/2017.
 */
public class MicroInstruction {

    /** Control for MPC */
    RegisterOp mpcOp = RegisterOp.Increment;

    /** Control for mbr */
    RegisterOp mbrOp = RegisterOp.None;

    /** Index of input to acMux */
    int acMuxIndex = -1;

    /** Index of input to marMux */
    int marMuxIndex = 1;

    /** Index of input to mbr */
    int mbrMuxIndex = -1;

    /** Control for ac */
    RegisterOp acOp = RegisterOp.None;

    /** Control for alu */
    ALUOp aluOp = ALUOp.None;

    /** Control for pc */
    RegisterOp pcOp = RegisterOp.None;

    /** Control for ir */
    RegisterOp irOp = RegisterOp.None;

    /** Control for mar */
    RegisterOp marOp = RegisterOp.None;

    public MicroInstruction setMbrOp(RegisterOp mbrOp) {
        this.mbrOp = mbrOp;
        return this;
    }

    public MicroInstruction setAcMuxIndex(int acMuxIndex) {
        this.acMuxIndex = acMuxIndex;
        return this;
    }

    public MicroInstruction setMbrMuxIndex(int mbrMuxIndex) {
        this.mbrMuxIndex = mbrMuxIndex;
        return this;
    }

    public MicroInstruction setMarMuxIndex(int marMuxIndex) {
        this.marMuxIndex = marMuxIndex;
        return this;
    }

    public MicroInstruction setAcOp(RegisterOp acOp) {
        this.acOp = acOp;
        return this;
    }

    public MicroInstruction setAluOp(ALUOp aluOp) {
        this.aluOp = aluOp;
        return this;
    }

    public MicroInstruction setPcOp(RegisterOp pcOp) {
        this.pcOp = pcOp;
        return this;
    }

    public MicroInstruction setIrOp(RegisterOp irOp) {
        this.irOp = irOp;
        return this;
    }

    public MicroInstruction setMarOp(RegisterOp marOp) {
        this.marOp = marOp;
        return this;
    }

    public MicroInstruction setMpcOp(RegisterOp mpcOp) {
        this.mpcOp = mpcOp;
        return this;
    }
}