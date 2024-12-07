package Logic.Graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pixel {
    BufferedImage buffer;
    Graphics gPixel;

    public Pixel() {
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        gPixel = buffer.createGraphics();
    }

    public void createPixel(int x, int y, Color c, Graphics g) {
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x, y, null);
    }
}
