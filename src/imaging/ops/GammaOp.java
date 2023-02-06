package imaging.ops;

import java.awt.image.BufferedImage;

public class GammaOp extends NullOp {
    private final double gamma;
    private final byte[] lookupTable;

    public GammaOp(double gamma) {
        this.gamma = gamma;
        var lookupTable = new byte[256];
        for (int i = 0; i < 256; i++) {
            lookupTable[i] = (byte) (255 * Math.pow(i / 255.0, gamma));
        }
        this.lookupTable = lookupTable;
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
                            col, row, band,
                            lookupTable[srcRaster.getSample(col, row, band)]
                    );
                }
            }
        }
        return dest;
    }
}
