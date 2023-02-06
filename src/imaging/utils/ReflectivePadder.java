package imaging.utils;

import java.awt.image.BufferedImage;

public class ReflectivePadder implements ImagePadder {
    @Override
    public int getSample(BufferedImage src, int col, int row, int band) {
        if (col < 0) {
            col = -1 - col;
        }
        if ((col / src.getWidth()) % 2 == 0) {
            col %= src.getWidth();
        } else {
            col = src.getWidth() - 1 - col % src.getWidth();
        }

        if (row < 0) {
            row = -1 - row;
        }
        if ((row / src.getHeight()) % 2 == 0) {
            row %= src.getHeight();
        } else {
            row = src.getHeight() - 1 - row % src.getHeight();
        }

        return src.getRaster().getSample(col, row, band);
    }
}
