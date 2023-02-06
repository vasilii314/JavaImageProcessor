package imaging.ops;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public abstract class BinaryImageOp extends NullOp {
    protected BufferedImage left;

    public BinaryImageOp(BufferedImage left) {
        this.left = left;
    }

    public abstract int combine(int s1, int s2);


    public BufferedImage createCompatibleDestImage(Rectangle bounds, ColorModel destCM) {
        return new BufferedImage(
                destCM,
                destCM.createCompatibleWritableRaster(bounds.width, bounds.height),
                destCM.isAlphaPremultiplied(),
                null
        );
    }

    @Override
    public BufferedImage filter(BufferedImage right, BufferedImage dest) {
        var leftBounds = left.getRaster().getBounds();
        var rightBounds = right.getRaster().getBounds();
        var intersection = leftBounds.intersection(rightBounds);

        if (dest == null) {
            if (left.getRaster().getNumBands() < right.getRaster().getNumBands()) {
                dest = createCompatibleDestImage(intersection, left.getColorModel());
            } else {
                dest = createCompatibleDestImage(intersection, right.getColorModel());
            }
        }

        var destRaster = dest.getRaster();

        for (int row = 0; row < destRaster.getHeight(); row++) {
            for (int col = 0; col < destRaster.getWidth(); col++) {
                for (int band = 0; band < destRaster.getNumBands(); band++) {
                    var s1 = left.getRaster().getSample(col, row, band);
                    var s2 = right.getRaster().getSample(col, row, band);
                    destRaster.setSample(col, row, band, combine(s1, s2));
                }
            }
        }
        return dest;
    }
}
