import java.util.logging.*;

/**
 * Created by kzlou on 3/31/2017.
 */
public class TestALU {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                return String.format("%s.%s(%s)\n", record.getSourceClassName(), record.getSourceMethodName(), record.getMessage());
            }
        });
        Logger.getLogger("").addHandler(handler);

        CPU cpu = new CPU();
        cpu.test();
    }

}
