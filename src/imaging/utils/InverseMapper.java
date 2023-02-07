package imaging.utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class InverseMapper {
    public Point2D inverseTransform(int dstX, int dstY, Point2D srcPt) {
        return inverseTransform(new Point2D.Double(dstX, dstY), srcPt);
    }

    public ImagePadder gerDefaultPadder() {
        return new ZeroPadder();
    }

    public void initializeMapping(BufferedImage src) {}

    public Rectangle getDestinationBounds(BufferedImage src) {
        return src.getRaster().getBounds();
    }

    public abstract Point2D inverseTransform(Point2D destP, Point2D srcPt);
}
