package brookite.games.goatcabbage.ui.utils;

import com.formdev.flatlaf.util.ScaledImageIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage loadImage(String imageName) throws IOException {
        URL imageURL = ImageLoader.class.getResource("/images/" + imageName);

        if (imageURL != null) {
            return ImageIO.read(imageURL);
        }
        throw new IOException("Не найдено изображение");
    }

    public static ImageIcon loadAsImageIcon(String imageName) throws IOException {
        return new ImageIcon(loadImage(imageName));
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
