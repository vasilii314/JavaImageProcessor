package imaging.utils;

import java.awt.image.BufferedImage;

public class TiledPadder implements ImagePadder {
    @Override
    public int getSample(BufferedImage src, int col, int row, int band) {
        col = col % src.getWidth();
        row = row % src.getHeight();

        if (col < 0) {
            col += src.getWidth();
        }

        if (row < 0) {
            row += src.getHeight();
        }
        return src.getRaster().getSample(col, row, band);
    }
}
