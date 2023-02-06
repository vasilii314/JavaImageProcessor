package imaging.ops;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class TransposeFlipOp extends NullOp {

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return new BufferedImage(
                destCM,
                destCM.createCompatibleWritableRaster(src.getHeight(), src.getWidth()),
                destCM.isAlphaPremultiplied(),
                null
        );
    }

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
                    destRaster.setSample(
                            row, col, band,
                            srcRaster.getSample(
                                    col, row, band
                            )
                    );
                }
            }
        }
        return dest;
    }
}
