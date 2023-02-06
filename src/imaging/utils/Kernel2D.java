package imaging.utils;

import java.awt.*;

public abstract class Kernel2D {
    private Rectangle bounds;

    public Kernel2D(int width, int height) {
        this.bounds = new Rectangle(-width / 2, -height / 2, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getWidth() {
        return bounds.width;
    }

    public int getHeight() {
        return bounds.height;
    }

    public abstract boolean isSeparable();
    public abstract double getValue(int col, int row);
    public abstract Kernel2D getRowVector();
    public abstract Kernel2D getColVector();
}
