package imaging.utils;

public class ImagingUtilities {
    public static int clamp(double sample, int min, int max) {
        var floorOfSample = (int) sample;

        if (floorOfSample <= min) {
            return floorOfSample;
        } else {
            return Math.min(floorOfSample, max);
        }
    }
}
