package imaging.ops;

import java.awt.image.BufferedImage;

public class LeftRightFlipOp extends NullOp {
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var srcRaster = src.getRaster();
        var destRaster = dest.getRaster();

        var axis = src.getWidth() / 2;

        for (int row = 0; row < srcRaster.getHeight(); row++) {
            for (int col = 0; col <= axis; col++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    destRaster.setSample(
                            srcRaster.getWidth() - col - 1, row, band,
                            srcRaster.getSample(
                                    col, row, band
                            )
                    );
                    destRaster.setSample(
                            col, row, band,
                            srcRaster.getSample(
                                    srcRaster.getWidth() - col - 1, row, band
                            )
                    );
                }
            }
        }
        return dest;
    }
}
