package imaging.utils;

import java.awt.image.BufferedImage;

public class ZeroPadder implements ImagePadder {
    @Override
    public int getSample(BufferedImage src, int col, int row, int band) {
        if (col < 0 || col >= src.getWidth() || row < 0 || row >= src.getHeight()) {
            return 0;
        } else {
            return src.getRaster().getSample(col, row, band);
        }
    }
}
