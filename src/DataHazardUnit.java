/**
 * Created by kzlou on 6/6/2017.
 */
public class DataHazardUnit {

    Register<Byte> SSelectorLatch;

    Register<Byte> TSelectorLatch;

    public void setSSelectorLatch(Output<Byte> sSelectorInput) {
        SSelectorLatch.setInput(sSelectorInput);
    }

    public void setTSelectorLatch(Output<Byte> tSelectorInput) {
        TSelectorLatch.setInput(tSelectorInput);
    }
}
