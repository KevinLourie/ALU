/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    RegisterBank registerBank;

    Decoder decoder;

    InstructionDecode() {
        registerBank = new RegisterBank();
        decoder = new Decoder();
    }

    public void init(Register<Integer> dataRegister, Register<Byte> addressRegister) {
        registerBank.init(null, null, addressRegister.getOutput(), dataRegister.getOutput());
        decoder.init(addressRegister.getOutput());
    }
}
