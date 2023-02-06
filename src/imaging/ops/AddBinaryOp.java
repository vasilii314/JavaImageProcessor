package imaging.ops;

import java.awt.image.BufferedImage;

public class AddBinaryOp extends BinaryImageOp {
    public AddBinaryOp(BufferedImage left) {
        super(left);
    }

    @Override
    public int combine(int s1, int s2) {
        return s1 + s2;
    }
}
