/**
 * Created by kzlou on 4/22/2017.
 */
public class InstructionDecode {

    RegisterBank registerBank;

    Decoder decoder;

    Register<Integer> dataRegister;

    Register<Byte> addressRegister;

    InstructionDecode(RegisterBank registerBank) {
        this.registerBank = registerBank;
        decoder = new Decoder();
        dataRegister = new Register<>("Data Register", 0);
        addressRegister = new Register<Byte>("Address Register", null);
    }

    public void init(Output<Integer> dataInput, Output<Byte> addressInput) {
        dataRegister.init(dataInput);
        addressRegister.init(addressInput);
        registerBank.init(null, null, addressInput, dataInput);
        decoder.init(addressInput);
    }

    public void cycle() {
        registerBank.cycle();
        dataRegister.cycle();
        addressRegister.cycle();
    }
}
