package imaging.utils;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public interface Interpolant {
    int interpolate(BufferedImage src, ImagePadder padder, Point2D point, int band);
}
