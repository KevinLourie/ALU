/**
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    Multiplexer<Short> writeBackDataMux;

    Register<Integer> memoryRegister;

    Register<Integer> writeBackRegister;

    WriteBack() {
        writeBackDataMux = new Multiplexer<>();
    }

    public void init(Output<Integer> memoryInput, Output<Integer> writeBackInput) {
        memoryRegister.init(memoryInput);
        writeBackRegister.init(writeBackInput);
        writeBackDataMux.init(writeBackRegister.getOutput());
    }

    public void cycle() {
        memoryRegister.cycle();
        writeBackRegister.cycle();
    }

}
