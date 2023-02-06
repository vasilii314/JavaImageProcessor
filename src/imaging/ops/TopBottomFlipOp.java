package imaging.ops;

import java.awt.image.BufferedImage;

public class TopBottomFlipOp extends NullOp {
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var srcRaster = src.getRaster();
        var destRaster = dest.getRaster();

        var axis = src.getHeight() / 2;

        for (int row = 0; row <= axis; row++) {
            for (int col = 0; col < srcRaster.getWidth(); col++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    destRaster.setSample(
                            col, srcRaster.getHeight() - row - 1, band,
                            srcRaster.getSample(
                                    col, row, band
                            )
                    );
                    destRaster.setSample(
                            col, row, band,
                            srcRaster.getSample(
                                    col, srcRaster.getHeight() - row - 1, band
                            )
                    );
                }
            }
        }
        return dest;
    }
}
