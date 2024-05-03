package brookite.games.goatcabbage.ui.utils;

import com.formdev.flatlaf.util.ScaledImageIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadAsImageIcon(String imageName) throws IOException {
        URL imageURL = ImageLoader.class.getResource("/images/" + imageName);

        if (imageURL != null) {
            BufferedImage image = ImageIO.read(imageURL);
            return new ImageIcon(image);
        } else {
            throw new IOException("Image not found");
        }
    }

    public static Icon loadAsScaledIcon(String imageName, float scale) throws IOException {
        ImageIcon icon = loadAsImageIcon(imageName);
        int width = (int) (icon.getIconWidth() * scale);
        int height = (int) (icon.getIconHeight() * scale);
        return new ScaledImageIcon(icon, width, height);
    }

    public static Icon loadAsScaledIcon(String imageName, int width, int height) throws IOException {
        ImageIcon icon = loadAsImageIcon(imageName);
        return new ScaledImageIcon(icon, width, height);
    }
}
