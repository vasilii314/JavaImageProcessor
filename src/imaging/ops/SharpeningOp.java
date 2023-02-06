package imaging.ops;

import imaging.utils.NonSeparableKernel;
import imaging.utils.ReflectivePadder;

import java.awt.image.BufferedImage;

public class SharpeningOp extends NullOp {
    private final double alpha;


    public SharpeningOp(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        var kernel = new NonSeparableKernel(3, 3, new double[] {
                -alpha / 9, -alpha / 9, -alpha / 9,
                -alpha / 9, (9 + 8 * alpha) / 9, -alpha / 9,
                -alpha / 9, -alpha / 9, -alpha / 9
        });


        var sharpenOp = new ConvolutionOp(true, new ReflectivePadder(), kernel);

        return sharpenOp.filter(src, null);
    }
}
