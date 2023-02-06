package imaging.utils;

public class SeparableKernel extends Kernel2D {
    protected Kernel2D row;
    protected Kernel2D col;

    public SeparableKernel(double[] colVector, double[] rowVector) {
        super(rowVector.length, colVector.length);
        this.row = new NonSeparableKernel(rowVector.length, 1, rowVector);
        this.col = new NonSeparableKernel(1, colVector.length, colVector);
    }

    @Override
    public boolean isSeparable() {
        return true;
    }

    @Override
    public double getValue(int col, int row) {
        throw new UnsupportedOperationException("Separable kernel");
    }

    @Override
    public Kernel2D getRowVector() {
        return row;
    }

    @Override
    public Kernel2D getColVector() {
        return col;
    }
}
