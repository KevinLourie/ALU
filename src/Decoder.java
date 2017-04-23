/**
 * Stateless. Input is opcode. Output is muxInput to control storage array. Translates machine code into microinstructions.
 * Translates the opcode to the muxInput of the corresponding microinstruction in the storage control.
 * Created by kzlou on 4/9/2017.
 */
public class Decoder implements Output<Short> {

    /** Array of instructions */
    short[] storageControlIndexes = new short[256];

    /** Current instruction */
    Output<Byte> input;

    Decoder() {
        // Loop through opcodes
        for(Opcode opcode : Opcode.values()) {
            storageControlIndexes[opcode.ordinal()] = opcode.getMicrocodeOrdinal();
        }
    }

    public void init(Output<Byte> input) {
        this.input = input;
    }

    /**
     * Return muxInput of storage control array
     * @return muxInput of storage control array
     */
    @Override
    public Short read() {
        int index = input.read();
        return storageControlIndexes[index];
    }
}
