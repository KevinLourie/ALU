/**
 * Assembly language
 * Created by kzlou on 4/13/2017.
 */
public enum Opcode {
    HALT(8),
    LDAx(9),
    STAx(12),
    ADDx(14),
    JMPx(20),
    BZx(21),
    NOT(23),
    SHR(24);

    Opcode(int microcodeOrdinal) {
        this.microcodeOrdinal = (short) microcodeOrdinal;
    }

    public short getMicrocodeOrdinal() {
        return microcodeOrdinal;
    }

    private short microcodeOrdinal;
}
