/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    Memory memory;

    Adder adder;

    Register<Integer> PC;

    Multiplexer<Integer> pcMux;

    InstructionFetch(Memory memory) {
        this.memory = memory;
        adder = new Adder(4);
        PC = new Register<>("PC", 0);
        pcMux = new Multiplexer<>();
    }

    public void init(Output<Integer> dataInput, Output<Integer> nextPC) {
        memory.init(dataInput, PC.getOutput());
        adder.init(nextPC);
        PC.init(nextPC);
        pcMux.init(nextPC);
    }

    public void cycle() {
        memory.cycle();
        PC.cycle();
    }
}
