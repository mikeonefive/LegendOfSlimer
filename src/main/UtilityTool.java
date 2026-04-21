package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());   // blank canvas
        Graphics2D g2 = scaledImage.createGraphics();                                       // whatever g2 is drawing will be saved in scaledImage
        g2.drawImage(original, 0, 0, width, height, null);                                  // image is drawn in this size
        g2.dispose();

        return scaledImage;
    }

    public static Font loadPixelFontFromFile() {
        try {
            InputStream fontFile = UtilityTool.class.getResourceAsStream("/fonts/PressStart2P.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
