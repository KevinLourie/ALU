/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionFetch {

    Memory memory;

    Adder adder;

    Register<Integer> PC;

    InstructionFetch(Memory memory) {
        this.memory = memory;
        adder = new Adder(4);
        PC = new Register<>("PC");
    }

    public void init(Register<Integer> dataInput, Register<Integer> nextPC) {
        memory.init(dataInput.getOutput(), PC.getOutput());
        adder.init(nextPC.getOutput());
        PC.init(nextPC.getOutput());
    }
}
