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

    Register(String name) {
        output = new Output<T>() {
            @Override
            public T read() {
                System.out.print(name);
                return data;
            }
        };
        this.name = name;
    }

    public void init(Output<T> input) {
        this.input = input;
    }

    public void cycle() {
        data = input.read();
        System.out.printf(" -> %s (%s)%n", name, data);
    }

    public Output<T> getOutput() {
        return output;
    }
}
