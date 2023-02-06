package imaging.ops;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

public class NullOp implements BufferedImageOp {
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, src.getColorModel());
        }

        var srcRaster = src.getRaster();
        var destRaster = dest.getRaster();

        for (int row = 0; row < srcRaster.getHeight(); row++) {
            for (int col = 0; col < srcRaster.getWidth(); col++) {
                for (int band = 0; band < destRaster.getNumBands(); band++) {
                    destRaster.setSample(
                            col, row, band,
                            srcRaster.getSample(
                                    col, row, band
                            )
                    );
                }
            }
        }
        return dest;
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return src.getRaster().getBounds();
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return new BufferedImage(
                destCM,
                destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
                destCM.isAlphaPremultiplied(),
                null
        );
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = (Point2D) srcPt.clone();
        } else {
            dstPt.setLocation(srcPt);
        }
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
}
