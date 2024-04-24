package brookite.games.goatcabbage.ui.utils;

import com.formdev.flatlaf.util.ScaledImageIcon;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadAsImageIcon(String imageName) throws IOException {
        URL imageURL = ImageLoader.class.getResource("/images/" + imageName);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            return icon;
        } else {
            throw new IOException("Image not found");
        }
    }

    public static Icon loadAsScaledIcon(String imageName, float scale) throws IOException {
        ImageIcon icon = loadAsImageIcon(imageName);
        if (icon != null) {
            int width = (int) (icon.getIconWidth() * scale);
            int height = (int) (icon.getIconHeight() * scale);
            return new ScaledImageIcon(icon, width, height);
        } else {
            return null;
        }
    }
}
