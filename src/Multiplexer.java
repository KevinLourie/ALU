/**
 * Created by kzlou on 4/1/2017.
 */
public class Multiplexer<T> implements Input<T> {

    MuxOp muxOp;

    Input<T> left;

    Input<T> right;

    Multiplexer(Input<MuxOp> muxOp, Input<T> left, Input<T> right) {
        this.muxOp = muxOp.read();
        this.left = left;
        this.right = right;
    }

    @Override
    public T read() {
        switch (muxOp) {
            case Left:
                return left.read();

            case Right:
                return right.read();
        }
        return null;
    }
}
