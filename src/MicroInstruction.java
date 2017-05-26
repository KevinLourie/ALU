/**
 * Stores operations for all parts of the CPU
 * Created by kzlou on 4/6/2017.
 */
public class MicroInstruction {

    /** True to halt the CPU */
    private boolean halt;

    /** ALU operation */
    private byte aluOp = AluOp.Add;

    /** Index of input to ALU mux */
    private int aluMuxIndex = 0;

    /** If 0, go to next PC. Otherwise, jump */
    private int jumpEnable = 0;

    /** Index of input to write back mux */
    private int wbMuxIndex = 0;

    /** True if a result should be written to a register*/
    private boolean wbEnable = false;

    /** True if result is written to memory */
    private boolean memoryWriteEnable = false;

    public byte getAluOp() {
        return aluOp;
    }

    public MicroInstruction setAluOp(byte aluOp) {
        this.aluOp = aluOp;
        return this;
    }

    public boolean isWait() {
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

    public boolean isWbEnable() {
        return wbEnable;
    }

    public MicroInstruction setWbEnable(boolean wbEnable) {
        this.wbEnable = wbEnable;
        return this;
    }

    public boolean isMemoryWriteEnable() {
        return memoryWriteEnable;
    }

    public MicroInstruction setMemoryWriteEnable(boolean memoryWriteEnable) {
        this.memoryWriteEnable = memoryWriteEnable;
        return this;
    }

    public MicroInstruction setHalt() {
        halt = true;
        return this;
    }
}