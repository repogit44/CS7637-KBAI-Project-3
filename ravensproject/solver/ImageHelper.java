package ravensproject.solver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ImageHelper {

    private static final int width = 184;
    private static final int height = 184;

    public void DrawImage(Set<Pixel> pixelSet, String title) {
        BufferedImage bufferedImage = buildBufferedImage(pixelSet);
        File out = new File(title + ".png");
        try {
            ImageIO.write(bufferedImage, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage buildBufferedImage(Set<Pixel> pixelSet) {
        BufferedImage imageOut =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] imageOutPixels = new int[width * height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int outIndex = y * width + x;
                if(!pixelSet.contains(new Pixel(x, y))) {
                    imageOutPixels[outIndex] = Color.WHITE.getRGB();
                }
            }
        }

        imageOut.setRGB(0, 0, width, height, imageOutPixels, 0, width);
        return imageOut;
    }

}
