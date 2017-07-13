/**
 * Created by kzlou on 7/11/2017.
 */
public enum Opcode {

    lda(0x23, false),
    reg(0x0, false),
    halt(0x8, false),
    beq(0x4, true),
    bgez(0x1, true),
    bgtz(0x7, true),
    blez(0x6, true),
    bltz(0x1, true),
    bne(0x5, true);

    public byte value;

    public boolean isBranch;

    Opcode(int value, boolean isBranch) {
        this.value = (byte)value;
        this.isBranch = isBranch;
    }

    public byte getValue() {
        return value;
    }

    public boolean isBranch() {
        return isBranch;
    }

}
