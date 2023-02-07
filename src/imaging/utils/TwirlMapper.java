package imaging.utils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TwirlMapper extends InverseMapper {
    private double xc;
    private double yc;
    private double minDim;
    private double strength;
    private int centerX;
    private int centerY;

    public TwirlMapper(double xc, double yc, double strength) {
        this.xc = xc;
        this.yc = yc;
        this.strength = strength;
    }

    @Override
    public void initializeMapping(BufferedImage src) {
        centerX = (int) (xc * src.getWidth());
        centerY = (int) (yc * src.getHeight());
        minDim = Math.min(src.getWidth(), src.getHeight());
    }

    @Override
    public Point2D inverseTransform(Point2D destP, Point2D srcPt) {
        if (srcPt == null) {
            srcPt = new Point2D.Double();
        }

        var dx = destP.getX() - centerX;
        var dy = destP.getY() - centerY;
        var r = Math.sqrt(dx * dx + dy * dy);
        double theta = Math.atan2(dx, dy);

        var srcX = r * Math.cos(theta + strength * (r - minDim) / minDim) + centerX;
        var srcY = r * Math.sin(theta + strength * (r - minDim) / minDim) + centerY;

        srcPt.setLocation(srcX, srcY);

        return srcPt;
    }
}
