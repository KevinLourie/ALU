import java.util.StringJoiner;

/**
 * Created by kzlou on 10/8/2017.
 */
public class Joiner {

    private StringJoiner stringJoiner;

    public Joiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        stringJoiner = new StringJoiner(delimiter, prefix, suffix);
        stringJoiner.setEmptyValue("");
    }

    public void add(CharSequence element) {
        if(element != null) {
            stringJoiner.add(element);
        }
    }

    @Override
    public String toString() {
        String concatenatedOutput = stringJoiner.toString();
        if(concatenatedOutput.length() == 0) {
            return null;
        }
        return concatenatedOutput;
    }
}
