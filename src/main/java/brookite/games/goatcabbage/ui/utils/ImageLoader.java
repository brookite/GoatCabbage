package brookite.games.goatcabbage.ui.utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadAsIcon(String imageName) throws IOException {
        URL imageURL = ImageLoader.class.getResource("/images/" + imageName);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            return icon;
        } else {
            throw new IOException("Image not found");
        }
    }

    public static ImageIcon loadAsIcon(String imageName, float scale) throws IOException {
        ImageIcon icon = loadAsIcon(imageName);
        if (icon != null) {
            int width = (int) (icon.getIconWidth() * scale);
            int height = (int) (icon.getIconHeight() * scale);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_FAST);
            return new ImageIcon(scaledImage);
        } else {
            return null;
        }
    }
}
