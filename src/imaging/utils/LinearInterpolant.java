package imaging.utils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class LinearInterpolant implements Interpolant {
    @Override
    public int interpolate(BufferedImage src, ImagePadder padder, Point2D point, int band) {
        var j = (int) Math.round(point.getX());
        var k = (int) Math.round(point.getY());
        var a = point.getX() - j;
        var b = point.getY() - k;

        var newX = (1 - a) * ((1 - b) * padder.getSample(src, j, k, band) + b * padder.getSample(src, j, k + 1, band));
        var newY = a * ((1- b) * padder.getSample(src, j + 1, k, band) + b * padder.getSample(src, j + 1, k + 1, band));

        return (int) (newX + newY);
    }
}
