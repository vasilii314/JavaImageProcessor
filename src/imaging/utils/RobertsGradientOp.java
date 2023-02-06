package imaging.utils;

import imaging.ops.AddBinaryOp;
import imaging.ops.ConvolutionOp;
import imaging.ops.InvertOp;
import imaging.ops.NullOp;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

public class RobertsGradientOp extends NullOp {
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var mainDiagonalOp = new ConvolutionOp(
                true,
                new ReflectivePadder(),
                new NonSeparableKernel(2, 2, new double[]{1, 0, 0, -1})
        );
        var subDiagonalOp = new ConvolutionOp(
                true,
                new ReflectivePadder(),
                new NonSeparableKernel(2, 2, new double[]{0, 1, -1, 0})
        );

        var mainDiagonalGradient = mainDiagonalOp.filter(src, null);
        var subDiagonalGradient = subDiagonalOp.filter(src, null);
        return new InvertOp().filter(new AddBinaryOp(mainDiagonalGradient).filter(subDiagonalGradient, null), null);
    }
}
