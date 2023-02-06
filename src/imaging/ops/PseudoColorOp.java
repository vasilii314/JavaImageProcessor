package imaging.ops;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;

public class PseudoColorOp extends NullOp {
    private final byte[] reds;
    private final byte[] greens;
    private final byte[] blues;

    public PseudoColorOp(Color[] palette) {
        this.reds = new byte[palette.length];
        this.greens = new byte[palette.length];
        this.blues = new byte[palette.length];

        for (int i = 0; i < palette.length; i++) {
            reds[i] = (byte) palette[i].getRed();
            greens[i] = (byte) palette[i].getGreen();
            blues[i] = (byte) palette[i].getBlue();
        }
    }

    private IndexColorModel getColorModel() {
        return new IndexColorModel(8, reds.length, reds, greens, blues);
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return new BufferedImage(
                src.getWidth(),
                src.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED,
                getColorModel()
        );
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        return super.filter(src, dest);
    }
}
