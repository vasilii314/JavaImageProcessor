package imaging.utils;

public class NonSeparableKernel extends Kernel2D {
    private double[] values;

    public NonSeparableKernel(int width, int height, double[] values) {
        super(width, height);
        this.values = values;
    }

    @Override
    public boolean isSeparable() {
        return false;
    }

    @Override
    public double getValue(int col, int row) {
        var index = (row - getBounds().y) * getWidth() + (col - getBounds().x);
        return values[index];
    }

    @Override
    public Kernel2D getRowVector() {
        throw new UnsupportedOperationException("Non separable kernel");
    }

    @Override
    public Kernel2D getColVector() {
        throw new UnsupportedOperationException("Non separable kernel");
    }
}
