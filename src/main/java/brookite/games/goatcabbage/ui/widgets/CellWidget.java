package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CellWidget extends JPanel {
    static ImageIcon icon;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("cell.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CellWidget() {
        super();
        setMaximumSize(new Dimension(128, 128));
        setPreferredSize(new Dimension(64, 64));
        setMinimumSize(new Dimension(48, 48));
    }

    public void addItem(CellItemWidget item) {
        add(item);
    }

    public void removeItem(CellItemWidget item) {
        remove(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (CellWidget.icon != null) {
            g.drawImage(CellWidget.icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
