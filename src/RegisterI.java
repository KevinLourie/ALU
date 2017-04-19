/**
 * IR register. Input is dataOutput. Output is opcode and its addressInput.
 * Created by kzlou on 4/6/2017.
 */
public class RegisterI extends Register {

    Output<Byte> opcodeOutput;

    Output<Integer> addressOutput;

    Output<Short> input;

    int data;

    RegisterI(String name) {
        super(name);
        opcodeOutput = new Output<Byte>() {

            @Override
            public Byte read() {
                System.out.print(name);
                return (byte) (data >> 24);
            }
        };

        addressOutput = new Output<Integer>() {

            @Override
            public Integer read() {
                System.out.print(name);
                return data & 0xFFFFFF;
            }
        };
    }

    public void init(Output<Short> input) {
        this.input = input;
    }

    public Output<Byte> getOpcodeOutput() {
        return opcodeOutput;
    }

    public Output<Integer> getAddressOutput() {
        return addressOutput;
    }

    public void cycle() {
        switch (registerOp) {
            case Store:
                int rawData = input.read();
                int opcode = (rawData << 16) & 0xFF000000;
                int address = rawData & 0x000000FF;
                data = opcode | address;
                System.out.printf(" -> %s %d%n", name, data);
                break;
            case None:
                break;
        }
    }
}
