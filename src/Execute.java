/**
 * Created by kzlou on 4/22/2017.
 */
public class Execute {

    ALU alu;

    Output<ALUOp> aluMux;

    Execute() {
        alu = new ALU();
        aluMux = new Multiplexer<>();
    }

    public void init(Register<Integer> dataRegister, Register<Integer> addressRegister) {
        alu.init(null, null,null);
    }

}
