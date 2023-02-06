import imaging.ops.*;
import imaging.utils.GaussianKernel;
import imaging.utils.NonSeparableKernel;
import imaging.utils.ReflectivePadder;
import imaging.utils.RobertsGradientOp;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var src = ImageIO.read(new File("orphan-girl.jpg"));
//        ImageComponent.showInFrame("Source", src);
        var operation = new SharpeningOp(2.);
        var dest = operation.filter(src, null);
//        ImageComponent.showInFrame("Result", dest);
        ImageIO.write(dest, "JPEG", new File("orphan-girl-sharp.jpg"));
    }
}