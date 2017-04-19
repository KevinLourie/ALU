/**
 * Accepts a short input.
 * Created by kzlou on 4/6/2017.
 */
public class Register16 extends Register implements Output<Short> {

    Output<Byte> opcodeOutput;

    Output<Integer> addressOutput;

    /**
     * Input to register
     */
    Output<Short> input;

    /**
     * Data in input
     */
    short data;

    Register16(String name) {
        super(name);
        opcodeOutput = new Output<Byte>() {

            @Override
            public Byte read() {
                System.out.print(name);
                return (byte) (data >> 8);
            }
        };

        addressOutput = new Output<Integer>() {

            @Override
            public Integer read() {
                System.out.print(name);
                return data & 0xFF;
            }
        };
    }

    public void init (Output<Short> a) {
        input = a;
    }

    /**
     * Write data to register
     */
    public void cycle() {
        switch (registerOp) {
            case Store:
                data = input.read();
                System.out.printf(" -> %s %d%n", name, data);
                break;
            case Increment:
                data++;
                System.out.println(name + " + 1 -> " + name);
                break;
            case Decrement:
                data--;
                System.out.println(name + " - 1 -> " + name);
                break;
            case Clear:
                data = 0;
                System.out.println(name + " = 0");
                break;
            case None:
                break;
        }
    }

    /**
     * Get data in register
     * @return data in register
     */
    @Override
    public Short read() {
        System.out.print(name);
        return data;
    }

    public Output<Byte> getOpcodeOutput() {
        return opcodeOutput;
    }

    public Output<Integer> getAddressOutput() {
        return addressOutput;
    }
}