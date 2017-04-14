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

    public void init(Output<Byte> input) {
        this.input = input;
    }

    @Override
    public Short read() {
        int index = input.read();
        return storageControlIndexes[index];
    }
}