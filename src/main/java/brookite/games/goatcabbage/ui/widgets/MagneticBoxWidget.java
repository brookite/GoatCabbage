package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.MagneticBox;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MagneticBoxWidget extends BoxWidget {
    private static final boolean RENDER_DEBUG_MAGNET_FIELD = false;
    private static ImageIcon magnetFieldIcon;

    public MagneticBoxWidget(MagneticBox entity) {
        super(entity);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (RENDER_DEBUG_MAGNET_FIELD) {
            if (magnetFieldIcon == null) {
                try {
                    magnetFieldIcon = ImageLoader.loadAsImageIcon("magnet_field.png");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение магнитного поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (magnetFieldIcon != null) {
                Image magnetField = magnetFieldIcon.getImage();
                MagneticBox box = (MagneticBox) getModelEntity();
                int x, y;
                if (box.interact(Direction.NORTH) == MagnetInteraction.ATTRACTION) {
                    x = getParent().getWidth() / 2 - 8;
                    y = 0;
                    g.drawImage(magnetField, x, y, 16, 16, this);
                }
                if (box.interact(Direction.WEST) == MagnetInteraction.ATTRACTION) {
                    x = 0;
                    y = getParent().getHeight() / 2 - 8;
                    g.drawImage(magnetField, x, y, 16, 16, this);
                }
                if (box.interact(Direction.SOUTH) == MagnetInteraction.ATTRACTION) {
                    x = getParent().getWidth() / 2 - 8;
                    y = getParent().getHeight() - 16;
                    g.drawImage(magnetField, x, y, 16, 16, this);
                }
                if (box.interact(Direction.EAST) == MagnetInteraction.ATTRACTION) {
                    x = getParent().getWidth() - 16;
                    y = getParent().getHeight() / 2 - 8;
                    g.drawImage(magnetField, x, y, 16, 16, this);
                }
            }
        }
    }
}
