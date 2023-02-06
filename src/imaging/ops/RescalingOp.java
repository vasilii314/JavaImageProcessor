package imaging.ops;

import imaging.utils.ImagingUtilities;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class RescalingOp extends NullOp {
    private final double[] gain;
    private final double[] bias;
    public RescalingOp(double gain, double bias) {
        this.gain = new double[] {gain, gain, gain};
        this.bias = new double[] {bias, bias, bias};
    }

    public RescalingOp(double[] gain, double[] bias) {
        this.gain = gain;
        this.bias = bias;
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
                    var rescaledSample = ImagingUtilities.clamp(
                            gain[band] * srcRaster.getSample(col, row, band) + bias[band],
                            0, 255
                    );
                    destRaster.setSample(col, row, band, rescaledSample);
                }
            }
        }
        return dest;
    }
}
