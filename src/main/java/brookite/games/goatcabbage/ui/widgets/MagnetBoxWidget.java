package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.MagnetBox;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagneticPole;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MagnetBoxWidget extends MagneticBoxWidget {
    private ImageIcon icon;

    public MagnetBoxWidget(MagnetBox entity) {
        super(entity);
    }

    private int getRotateAngle() {
        MagnetBox magnet = (MagnetBox) getModelEntity();
        Direction dir = magnet.getMagnetDirection();
        return switch (dir) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case EAST -> 90;
            case WEST -> -90;
        };
    }

    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                BufferedImage image = ImageLoader.loadImage("magnet_box.png");
                image = rotateImage(image, getRotateAngle());
                if (needImageMirroring()) {
                    image = createMirror(image, Math.abs(getRotateAngle()) == 90);
                }
                icon = new ImageIcon(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }

    private boolean needImageMirroring() {
        MagnetBox magnet = (MagnetBox) getModelEntity();
        Direction poleDirection = magnet.getMagnetDirection().clockwise();
        MagneticPole pole = magnet.getMagneticPoleByDirection(poleDirection);
        if (pole.equals(MagneticPole.NORTH) && poleDirection.equals(Direction.NORTH)) {
            return true;
        } else if (pole.equals(MagneticPole.NORTH) && poleDirection.equals(Direction.SOUTH)) {
            return true;
        } else if (pole.equals(MagneticPole.NORTH) && poleDirection.equals(Direction.WEST)) {
            return true;
        } else return pole.equals(MagneticPole.NORTH) && poleDirection.equals(Direction.EAST);
    }

    private static BufferedImage rotateImage(BufferedImage buffImage, double angle) {
        // SOURCE: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
        double radian = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radian));
        double cos = Math.abs(Math.cos(radian));

        int width = buffImage.getWidth();
        int height = buffImage.getHeight();

        int nWidth = (int) Math.floor((double) width * cos + (double) height * sin);
        int nHeight = (int) Math.floor((double) height * cos + (double) width * sin);

        BufferedImage rotatedImage = new BufferedImage(
                nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = rotatedImage.createGraphics();

        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);
        graphics.rotate(radian, (double) (width / 2), (double) (height / 2));
        graphics.drawImage(buffImage, 0, 0, null);
        graphics.dispose();

        return rotatedImage;
    }

    public BufferedImage createMirror(BufferedImage image, boolean vertical)
    {
        // SOURCE: https://stackoverflow.com/questions/71280950/mirroring-an-image-in-java
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        if (vertical) {
            g2d.translate(0, bi.getHeight());
            g2d.scale(1, -1);
        } else {
            g2d.translate(bi.getWidth(), 0);
            g2d.scale(-1, 1);
        }

        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return bi;
    }
}
