package imaging.utils;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Mask {
    private boolean[] mask;
    private int width;
    private int height;

    public Mask(boolean[] mask, int width, int height) {
        this.mask = mask;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isIncluded(int x, int y) {
        return mask[(y + height / 2) * width + (x + width / 2)];
    }

    public int getSample(int x, int y) {
        return mask[(y + height / 2) * width + (x + width / 2)] ? 255 : 0;
    }

    public int getSize() {
        var count = 0;
        for (boolean b : mask) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    public Rectangle getBounds() {
        return new Rectangle(-width / 2, -height / 2, width, height);
    }

    public Mask invert() {
        var result = new Mask(Arrays.copyOf(mask, mask.length), width, height);
        for (int i = 0; i < mask.length; i++) {
            result.mask[i] = !mask[i];
        }
        return result;
    }
}
