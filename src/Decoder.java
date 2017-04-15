/**
 * Stateless. Input is opcode. Output is index to control storage array. Translates machine code into microinstructions.
 * Translates the opcode to the index of the corresponding microinstruction in the storage control.
 * Created by kzlou on 4/9/2017.
 */
public class Decoder implements Output<Short> {

    /** Array of instructions */
    short[] storageControlIndexes = new short[256];

    /** Current instruction */
    Output<Byte> input;

    Decoder() {
        storageControlIndexes[Opcode.LDAx.ordinal()] = 10;
        storageControlIndexes[Opcode.STAx.ordinal()] = 13;
        storageControlIndexes[Opcode.ADDx.ordinal()] = 15;
        storageControlIndexes[Opcode.ANDx.ordinal()] = 18;
        storageControlIndexes[Opcode.JMPx.ordinal()] = 21;
        storageControlIndexes[Opcode.BZx.ordinal()] = 22;
        storageControlIndexes[Opcode.NOT.ordinal()] = 24;
        storageControlIndexes[Opcode.SHR.ordinal()] = 25;
    }

    public void init(Output<Byte> input) {
        this.input = input;
    }

    /**
     * Return index of storage control array
     * @return index of storage control array
     */
    @Override
    public Short read() {
        int index = input.read();
        return storageControlIndexes[index];
    }
}
