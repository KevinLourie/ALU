/**
 * Created by kzlou on 4/22/2017.
 */
public class MemoryAccess {

    Multiplexer<Integer> addressMux;

    Memory memory;

    MemoryAccess(Memory memory) {
        this.memory = memory;
        addressMux = new Multiplexer<>();
    }

    public void init(Register<Integer> dataInput, Register<Integer> nextPC) {
        memory.init(dataInput.getOutput(), nextPC.getOutput());
        addressMux.init(null);
    }

}
