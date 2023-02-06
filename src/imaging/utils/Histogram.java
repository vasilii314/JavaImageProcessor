package imaging.utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Histogram {
    private int[][] counts;
    private int totalSamples;
    private double[][] cdf;

    public Histogram(BufferedImage src) {
        var raster = src.getRaster();
        counts = new int[raster.getNumBands()][256];
        totalSamples = src.getWidth() * src.getHeight();

        for (int row = 0; row < raster.getHeight(); row++) {
            for (int col = 0; col < raster.getWidth(); col++) {
                for (int band = 0; band < raster.getNumBands(); band++) {
                    var sample = raster.getSample(col, row, band);
                    counts[band][sample]++;
                }
            }
        }
        cdf = new double[raster.getNumBands()][256];

        for (int band = 0; band < raster.getNumBands(); band ++) {
            cdf[band] = getCDF(band);
        }
        System.out.println("histogram ready");
    }

    public int[] getCounts(int band) {
        var result = new int[256];
        System.arraycopy(counts, 0, result, 0, counts[band].length);
        return result;
    }

    public double[] getNormalizedHistogram(int band) {
        return Arrays.stream(counts[band]).mapToDouble(h -> (double) h / totalSamples).toArray();
    }

    private double[] getCDF(int band) {
        var cdf = getNormalizedHistogram(band);
        for (int i = 1; i < cdf.length; i++) {
            cdf[i] += cdf[i - 1];
        }
        return cdf;
    }

    public int cdfFromSample(int band, int sample) {
        return (int) (255 * cdf[band][sample]);
    }
}
