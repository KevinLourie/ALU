/**
 * General register class.
 * Created by kzlou on 4/1/2017.
 */
public class Register<T> {

    /**
     * Name of register
     */
    String name;

    /**
     * Data type of register
     */
    T data;

    /**
     * Output of register
     */
    Output<T> output;


    Output<T> input;

    Register(String name, T initial) {
        this.name = name;
        data = initial;
        /**
         * Prints the name of the register and its data
         */
        output = new Output<T>() {
            @Override
            public T read() {
                System.out.print(name);
                return data;
            }
        };
    }

    /**
     * Initializes input
     * @param input input
     */
    public void init(Output<T> input) {
        this.input = input;
    }

    /**
     * Store data in register
     */
    public void cycle() {
        data = input.read();
        System.out.printf(" -> %s (%s)%n", name, data);
    }

    /**
     * Getter for output
     * @return output
     */
    public Output<T> getOutput() {
        return output;
    }
}
