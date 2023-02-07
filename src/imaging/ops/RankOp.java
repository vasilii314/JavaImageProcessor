package imaging.ops;

import imaging.utils.ImagePadder;
import imaging.utils.Mask;
import imaging.utils.RBTree;
import imaging.utils.ReflectivePadder;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RankOp extends NullOp {
    private Mask mask;
    private int rank;
    private ImagePadder padder;

    public RankOp(Mask mask, int rank) {
        this.mask = mask;
        this.rank = Math.abs(rank);
        this.padder = new ReflectivePadder();
    }

    private int getSampleOfRank(BufferedImage src, int col, int row, int band) {
        var tree = new RBTree<Integer>();
        var maskBounds = mask.getBounds();

        for (int maskRow = -maskBounds.height / 2; maskRow < (maskBounds.height / 2) + (maskBounds.height % 2); maskRow++) {
            for (int maskCol = -maskBounds.width / 2; maskCol < (maskBounds.width / 2) + (maskBounds.width % 2); maskCol++) {
                if (mask.isIncluded(maskCol, maskRow)) {
                    tree.insert(padder.getSample(src, col + maskCol, row + maskRow, band));
                }
            }
        }
        var t = tree.traverse();
        return t.get(rank);
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var srcRaster = src.getRaster();
        var destRaster = dest.getRaster();

        var cdl = new CountDownLatch(srcRaster.getNumBands());

        for (int band = 0; band < srcRaster.getNumBands(); band++) {
            int finalBand = band;
            new Thread(() -> {
                for (int row = 0; row < srcRaster.getHeight(); row++) {
                    for (int col = 0; col < srcRaster.getWidth(); col++) {
                        var sampleOfRank = getSampleOfRank(src, col, row, finalBand);
                        destRaster.setSample(col, row, finalBand, sampleOfRank);
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
