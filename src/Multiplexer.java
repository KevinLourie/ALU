/**
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> implements Input<T> {

    Input<MuxOp> muxControl;

    Input<T> left;

    Input<T> right;

    Multiplexer(Input<T> left, Input<T> right, Input<MuxOp> muxControl) {
        this.muxControl = muxControl;
        this.left = left;
        this.right = right;
    }

    @Override
    public T read() {
        switch (muxControl.read()) {
            case Left:
                return left.read();
            case Right:
                return right.read();
        }
        return null;
    }
}
