/**
 * Stores operations for all parts of the CPU
 * Created by kzlou on 4/6/2017.
 */
public class MicroInstruction {

    /** True to halt the CPU */
    private byte halt = (byte)0;

    /** ALU operation */
    private byte aluOp = AluOp.Add;

    /** Index of input to ALU mux */
    private int aluMuxIndex = 0;

    /** If 0, go to next PC. Otherwise, jump */
    private int jumpEnable = 0;

    /** Index of input to write back mux */
    private int wbMuxIndex = 0;

    /** One if a result should be written to a register*/
    private byte wbEnable = 0;

    /** One if result is written to memory */
    private byte memoryWriteEnable = 0;

    public byte getAluOp() {
        return aluOp;
    }

    public MicroInstruction setAluOp(byte aluOp) {
        this.aluOp = aluOp;
        return this;
    }

    public byte isWait() {
        return halt;
    }

    public int getAluMuxIndex() {
        return aluMuxIndex;
    }

    public MicroInstruction setAluMuxIndex(int aluMuxIndex) {
        this.aluMuxIndex = aluMuxIndex;
        return this;
    }

    public int getJumpEnable() {
        return jumpEnable;
    }

    public MicroInstruction setJumpEnable(int jumpEnable) {
        this.jumpEnable = jumpEnable;
        return this;
    }

    public int getWbMuxIndex() {
        return wbMuxIndex;
    }

    public MicroInstruction setWbMuxIndex(int wbMuxIndex) {
        this.wbMuxIndex = wbMuxIndex;
        return this;
    }

    public byte isWbEnable() {
        return wbEnable;
    }

    public MicroInstruction setWbEnable(byte wbEnable) {
        this.wbEnable = wbEnable;
        return this;
    }

    public byte isMemoryWriteEnable() {
        return memoryWriteEnable;
    }

    public MicroInstruction setMemoryWriteEnable(byte memoryWriteEnable) {
        this.memoryWriteEnable = memoryWriteEnable;
        return this;
    }

    public MicroInstruction setHalt() {
        halt = (byte)1;
        return this;
    }
}