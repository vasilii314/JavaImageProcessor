package imaging.utils;

public class GaussianKernel extends SeparableKernel {

    private double[] vectorKernel;

    public GaussianKernel(double alpha, double sigma) {
        super(
                GaussianCoefficients.getGaussianCoefficients(alpha, sigma),
                GaussianCoefficients.getGaussianCoefficients(alpha, sigma)
        );
    }
}
