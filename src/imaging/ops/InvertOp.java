package imaging.ops;

import java.awt.image.BufferedImage;

public class InvertOp extends NullOp {
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var srcRaster = src.getRaster();
        var destRaster = dest.getRaster();

        for (int row = 0; row < srcRaster.getHeight(); row++) {
            for (int col = 0; col < srcRaster.getWidth(); col++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    var sample = srcRaster.getSample(col, row, band);
                    destRaster.setSample(col, row, band, 255 - sample);
                }
            }
        }
        return dest;
    }
}
