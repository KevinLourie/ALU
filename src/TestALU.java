import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kzlou on 3/31/2017.
 */
public class TestALU {

    public static void main(String[] args) {
        Logger.getLogger("Bus").setLevel(Level.FINE);
        Handler ch = new ConsoleHandler();
        Logger.getLogger("Bus").addHandler(ch);

        CPU cpu = new CPU();
        cpu.test2();
    }

}
