import java.io.BufferedReader;
import java.io.CharArrayReader;
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
            line = storeSymbol(line, pass);
            if (pass == 2) {
                decodeInstruction(line);
            }
            address+=4;
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

    public void decodeInstruction(String line) {
        String[] arr = line.split(",");
        int result = 0;
        String type;
        int[] parts = new int[arr.length];
        Opcode opcode = null;
        if(parts.length == 1) {
            parts[0] =  Integer.parseUnsignedInt(arr[0], 16);
        }
        else {
            // Checking for symbol on line
            if(line.contains(":")) {
                type = line.substring(line.indexOf(":")+1, line.indexOf(","));
                opcode = Opcode.valueOf(type);
            }
            else {
                opcode = Opcode.valueOf(arr[0]);
            }
            parts[0] = opcode.getValue();
            for (int i = 1; i < arr.length; i++) {
                // Checking for symbol as jump address
                if (Character.isAlphabetic(arr[i].charAt(0))) {
                    parts[i] = symbols.get(arr[i]);
                }
                else {
                    parts[i] = Integer.parseUnsignedInt(arr[i], 16);
                }
            }
        }
        switch(arr.length) {
            case 1:
                    result = parts[0];
                    break;
            case 2: {
                int jumpTarget = parts[1];
                result = (opcode.getValue() << 26) + jumpTarget;
                break;
            }
            case 4: {
                int s = parts[1];
                int t = parts[2];
                int immediate = parts[3];
                // For branch instructions
                if (opcode.isBranch) {
                    int offset = (immediate - address - 4) & 0xFFFF;
                    result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + offset;
                }
                // For non-branch instructions
                else {
                    result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + immediate;
                }
                break;
            }
            case 6: {
                int s = parts[1];
                int t = parts[2];
                result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + (parts[3] << 11) + (parts[4] << 6) + parts[5];
            }
        }
        memory[address/4] = result;
    }

    /**
     * Return number for type of instruction
     * @param line instruction
     */
    private void decodeAndStoreInstruction(String line) {
        int result = 0;
        String[] arr = line.split(",");
        int[] parts = new int[arr.length];
        Opcode opcode = null;
        // Line contains data not an instruction
        if(parts.length == 1) {
            parts[0] =  Integer.parseUnsignedInt(arr[0], 16);
        }
        else {
            opcode = Opcode.valueOf(arr[0]);
            parts[0] = opcode.getValue();
            for (int i = 1; i < arr.length; i++) {
                parts[i] = Integer.parseUnsignedInt(arr[i], 16);
            }
        }
        switch (parts.length) {
            case 1:
                result = parts[0];
                break;
            case 2: {
                int jumpTarget = parts[1];
                result = (opcode.getValue() << 26) + jumpTarget;
                break;
            }
            case 4: {
                int s = parts[1];
                int t = parts[2];
                int immediate = parts[3];
                // For branch instructions
                if(opcode.isBranch) {
                    int offset = (immediate - address - 4) & 0xFFFF;
                    result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + offset;
                }
                // For non-branch instructions
                else {
                    result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + immediate;
                }
                break;
            }
            case 6: {
                int s = parts[1];
                int t = parts[2];
                result = (opcode.getValue() << 26) + (s << 21) + (t << 16) + (parts[3] << 11) + (parts[4] << 6) + parts[5];
            }
        }
        memory[address/4] = result;
    }

}
