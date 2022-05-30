import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;

public class ImageIconTest {
    static BufferedImage image;
    public static void main(String[] args) {
        Object obj = new Object();
      try {
        image  = ImageIO.read(obj.getClass().getResourceAsStream("/GUIStuff/logo.png"));
      } catch (IOException e) {
          e.printStackTrace();
      }
      JFrame frame = new JFrame();
      frame.setIconImage(image);
      frame.setVisible(true);
    }
}