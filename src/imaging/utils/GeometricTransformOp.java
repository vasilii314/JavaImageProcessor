package imaging.utils;

import imaging.ops.NullOp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.concurrent.CountDownLatch;

public class GeometricTransformOp extends NullOp {
    private Interpolant interpolant;
    private InverseMapper mapper;
    private ImagePadder padder;

    public GeometricTransformOp(Interpolant interpolant, InverseMapper mapper) {
        this.interpolant = interpolant;
        this.mapper = mapper;
        this.padder = mapper.gerDefaultPadder();
    }

    public GeometricTransformOp(Interpolant interpolant, InverseMapper mapper, ImagePadder padder) {
        this.interpolant = interpolant;
        this.mapper = mapper;
        this.padder = padder;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var bounds = mapper.getDestinationBounds(src);
        mapper.initializeMapping(src);

        var dstPt = new Point2D.Double();
        var srcPt = new Point2D.Double();

        var destRaster = dest.getRaster();

        for (int row = 0; row < destRaster.getHeight(); row++) {
            for (int col = 0; col < destRaster.getWidth(); col++) {
                dstPt.setLocation(col + bounds.x, row + bounds.y);
                mapper.inverseTransform(dstPt, srcPt);
                for (int band = 0; band < destRaster.getNumBands(); band++) {
                    var sample = interpolant.interpolate(src, padder, srcPt, band);
                        destRaster.setSample(col, row, band, sample);
                }
            }
        }
        return dest;
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        var bounds = mapper.getDestinationBounds(src);
        return new BufferedImage(
                destCM,
                destCM.createCompatibleWritableRaster((int) bounds.getWidth(), (int) bounds.getHeight()),
                destCM.isAlphaPremultiplied(),
                null
        );
    }
}
