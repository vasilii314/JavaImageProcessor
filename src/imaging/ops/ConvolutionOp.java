package imaging.ops;

import imaging.utils.ImagePadder;
import imaging.utils.Kernel2D;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

public class ConvolutionOp extends NullOp {
    private final boolean takeAbsoluteValue;
    private final ImagePadder padder;
    private final Kernel2D kernel;

    public ConvolutionOp(boolean takeAbsoluteValue, ImagePadder padder, Kernel2D kernel) {
        this.takeAbsoluteValue = takeAbsoluteValue;
        this.padder = padder;
        this.kernel = kernel;
        System.out.println(kernel.getBounds());
    }

    private double convolveAtPoint(BufferedImage src, int col, int row, int band) {
        double sum = 0;
        for (int kernelRow = -kernel.getBounds().height / 2; kernelRow < (kernel.getBounds().height / 2) + (kernel.getBounds().height % 2); kernelRow++) {
            for (int kernelCol = -kernel.getBounds().width / 2; kernelCol < (kernel.getBounds().width / 2) + (kernel.getBounds().width % 2); kernelCol++) {
                var sample = padder.getSample(src, col - kernelCol, row - kernelRow, band);
                sum += kernel.getValue(kernelCol, kernelRow) * sample;
            }
        }
        return takeAbsoluteValue ? Math.abs(sum) : sum;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        if (kernel.isSeparable()) {
            var firstPass = new ConvolutionOp(takeAbsoluteValue, padder, kernel.getColVector());
            var secondPass = new ConvolutionOp(takeAbsoluteValue, padder, kernel.getRowVector());
            return secondPass.filter(firstPass.filter(src, null), dest);
        } else {
            var srcRaster = src.getRaster();
            var destRaster = dest.getRaster();

            var cdl = new CountDownLatch(srcRaster.getNumBands());

            for (int band = 0; band < srcRaster.getNumBands(); band++) {
                int finalBand = band;
                new Thread(() -> {
                    for (int row = 0; row < srcRaster.getHeight(); row++) {
                        for (int col = 0; col < srcRaster.getWidth(); col++) {
                            var conv = convolveAtPoint(src, col, row, finalBand);
                            destRaster.setSample(col, row, finalBand, conv);
                        }
                    }
                    cdl.countDown();
                }).start();
            }
            try {
                cdl.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return dest;
        }
    }
}
