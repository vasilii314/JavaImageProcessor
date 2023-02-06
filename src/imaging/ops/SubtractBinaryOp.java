package imaging.ops;

import java.awt.image.BufferedImage;

public class SubtractBinaryOp extends BinaryImageOp {

    public SubtractBinaryOp(BufferedImage left) {
        super(left);
    }

    @Override
    public int combine(int s1, int s2) {
        return Math.abs(s1 - s2);
    }
}
