/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    Multiplexer<Integer> addressMux;

    Memory memory;

    Register<Integer> dataRegister;

    Register<Integer> addressRegister;

    MemoryAccess(Memory memory) {
        this.memory = memory;
        addressMux = new Multiplexer<>();
    }

    public void init(Output<Integer> dataInput, Output<Integer> nextPC) {
        dataRegister.init(dataInput);
        addressRegister.init(nextPC);
        memory.init(dataRegister.getOutput(), addressRegister.getOutput());
        addressMux.init(addressRegister.getOutput());
    }

    public void cycle() {
        memory.cycle();
        dataRegister.cycle();
        addressRegister.cycle();
    }

}
