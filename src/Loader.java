import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by kzlou on 6/27/2017.
 */
public class Loader {

    /** Collection of symbols */
    HashMap<String, Integer> symbols;

    /** Instruction memory */
    private int[] memory;

    /** Address of symbol */
    int address;

    String file;

    Loader(String file, int[] memory) {
        symbols = new HashMap<>();
        this.file = file;
        this.memory = memory;
    }

    /**
     * Read file
     * @param path file
     * @param pass 1 for pulling out symbols, 2 for storing instructions
     * @throws IOException
     */
    public void readFile(String path, int pass) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        String[] arr;
        address = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.compareTo(".data") == 0) {
                address = 0x400;
                continue;
            }
            address+=4;
            if (pass == 1) {
                line = storeSymbol(line, pass);
            } else if (pass == 2) {
                decodeAndStoreInstruction(line);
            }
        }
    }

    /**
     * Store symbol in hash map
     * @param line line to read
     * @param pass if 2, just return line as is
     * @return instruction
     */
    private String storeSymbol(String line, int pass) {
        int location = line.indexOf(":");
        if (location == -1) {
            return line;
        }
        String symbol = line.substring(0, location);
        line = line.substring(location+1);
        if(pass == 1) {
            symbols.put(symbol, address);
        }
        return line;
    }

    /**
     * Return number for type of instruction
     * @param line instruction
     * @return 2 for jump, 4 for immediate, 6 for register
     */
    private int decodeAndStoreInstruction(String line) {
        int length;
        int result = 0;
        int[] parts = convertToIntegerArray(line);
        length = parts.length;
        switch (length) {
            case 1:
                result = parts[0];
                break;
            case 2:
                result = (parts[0] << 26) + parts[1];
                break;
            case 4:
                result = (parts[0] << 26) + (parts[1] << 21) + (parts[2] << 16) + parts[3];
                break;
            case 6:
                result = (parts[0] << 26) + (parts[1] << 21) + (parts[2] << 16) + (parts[3] << 11) + (parts[4] << 6) + parts[5];
        }
        memory[address/4] = result;
        return result;
    }

    private int[] convertToIntegerArray(String line) {
        String[] arr = line.split(",");
        int[] parts = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            parts[i] = Integer.parseUnsignedInt(arr[i], 16);
        }
        return parts;
    }

    public HashMap<String, Integer> getSymbols() {
        return symbols;
    }

    public Integer getSymbol(String key) {
        return symbols.get(key);
    }

}
