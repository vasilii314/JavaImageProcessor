package imaging.utils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class NearestNeighborInterpolant implements Interpolant {
    @Override
    public int interpolate(BufferedImage src, ImagePadder padder, Point2D point, int band) {
        var x = (int) Math.round(point.getX());
        var y = (int) Math.round(point.getY());

        return padder.getSample(src, x, y, band);
    }
}
