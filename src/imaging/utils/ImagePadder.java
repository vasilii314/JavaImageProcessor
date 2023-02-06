package imaging.utils;

import java.awt.image.BufferedImage;

public interface ImagePadder {
    int getSample(BufferedImage src, int col, int row, int band);
}
