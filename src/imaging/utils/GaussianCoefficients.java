package imaging.utils;

public class GaussianCoefficients {
    private static double[] coefficients;

    public static double[] getGaussianCoefficients(double alpha, double sigma) {
        if (coefficients == null) {
            var w = (int) Math.ceil(alpha * sigma);
            var result = new double[2 * w + 1];
            for (int n = 0; n <= w; n++) {
                var coefficient = Math.exp(-(n * n) / (2 * sigma * sigma)) / (Math.sqrt(2 * Math.PI) * sigma);
                result[w + n] = coefficient;
                result[w - n] = coefficient;
            }
            coefficients = result;
        }
        return coefficients;
    }
}
