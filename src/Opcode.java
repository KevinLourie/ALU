/**
 * Assembly language
 * Created by kzlou on 4/13/2017.
 */
public enum Opcode {
    HALT(9),
    LDAx(10),
    STAx(13),
    ADDx(15),
    ANDx(18),
    JMPx(21),
    BZx(22),
    NOT(24),
    SHR(25);

    Opcode(int microcodeOrdinal) {
        this.microcodeOrdinal = (short) microcodeOrdinal;
    }

    public short getMicrocodeOrdinal() {
        return microcodeOrdinal;
    }

    private short microcodeOrdinal;
}
