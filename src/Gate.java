/**
 * Generic wbEnableGate which takes n precomputedOutput and outputs the combination
 * Created by kzlou on 7/30/2017.
 */
public class Gate {

    /** Constant for and of two precomputedOutput */
    static int[] and2 = {0, 0, 0, 1};

    /** Constant for or of two precomputedOutput */
    static int[] or2 = {0, 1, 1, 1};

    /**
     * Constant for excusive or of two precomputedOutput
     */
    static int[] xor2 = {0, 1, 1, 0};

    static int[] nand2 = {1, 1, 1, 0};

    static int[] nor2 = {1, 0, 0, 0};

    static int[] xnor2 = {1, 0, 0, 1};

    /**
     * Possible precomputedOutput. The index is a set of bits. The bits are an input to the gate.
     * Each element is a single bit. The bit represents the output of the gate for that combination of inputs.
     */
    int[] precomputedOutput;

    /** Input array */
    Output<Value8>[] inputs;

    /** Either 0 or 1 for output*/
    Output<Value8> gateOutput;

    String gateName;

    /**
     * Constructor
     *
     * @param precomputedOutput outputs for each input combination
     */
    Gate(String gateName, int[] precomputedOutput) {
        inputs = new Output[31 - Integer.numberOfLeadingZeros(precomputedOutput.length)];
        this.precomputedOutput = precomputedOutput;
        this.gateName = gateName;
        gateOutput = () -> {
            String name = "";
            int index = 0;
            for (int i = inputs.length - 1; i >= 0; i--) {
                index = (index << 1) + inputs[i].read().intValue();
                name += inputs[i] + (i==0 ? ")" : ",");
            }
            return new Value8(precomputedOutput[index]);
        };
    }

    public String toString() {
        return gateName;
    }

    /**
     * Setter for inputs
     * @param index index for input
     * @param input what to put at index
     */
    public void setInput(int index, Output<Value8> input) {
        inputs[index] = input;
    }

    /**
     * Getter for gate output
     * @return gate output
     */
    public Output<Value8> getOutput() {
        return gateOutput;
    }
}
