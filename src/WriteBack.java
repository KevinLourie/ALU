/**
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    Multiplexer<Short> writeBackDataMux;

    WriteBack() {
        writeBackDataMux = new Multiplexer<>();
    }

    public void init(Register<Integer> memoryRegister, Register<Integer> writeBack) {
        writeBackDataMux.init(null);
    }

}
