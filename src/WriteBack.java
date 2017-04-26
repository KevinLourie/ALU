/**
 * Created by kzlou on 4/22/2017.
 */
public class WriteBack {

    /**
     * Choose what to write back
     */
    Multiplexer<Short> writeBackDataMux;

    /**
     * Main memory register
     */
    Register<Integer> memoryRegister;

    /**
     * Register containing what to write back
     */
    Register<Integer> writeBackRegister;

    WriteBack(Cycler cycler) {
        writeBackDataMux = new Multiplexer<>();
    }

    /**
     * Initialize memory register and write back register
     * @param memoryInput memory register input
     * @param writeBackInput write back register input
     */
    public void init(Output<Integer> memoryInput, Output<Integer> writeBackInput) {
        // TODO: add correct enable inputs
        memoryRegister.init(memoryInput, null);
        writeBackRegister.init(writeBackInput, null);
        writeBackDataMux.init(writeBackRegister.getOutput());
    }
}
