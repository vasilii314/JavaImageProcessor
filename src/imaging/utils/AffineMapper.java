package imaging.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class AffineMapper extends InverseMapper {
    private AffineTransform inverseXfrm;
    private AffineTransform forwardXfrm;

    public AffineMapper(AffineTransform xfrm) throws NoninvertibleTransformException {
        this.forwardXfrm = xfrm;
        this.inverseXfrm = xfrm.createInverse();
    }

    @Override
    public Rectangle getDestinationBounds(BufferedImage src) {
        return forwardXfrm.createTransformedShape(src.getRaster().getBounds()).getBounds();
    }

    @Override
    public Point2D inverseTransform(Point2D destP, Point2D srcPt) {
        return inverseXfrm.transform(destP, srcPt);
    }
}
