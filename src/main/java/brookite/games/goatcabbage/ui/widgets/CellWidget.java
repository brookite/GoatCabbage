package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.ui.utils.ImageLoader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CellWidget extends JPanel {
    static ImageIcon icon;
    private final FieldPanel parent;

    public static final int SMALLEST_SIZE = 8;
    public static final int SMALL_SIZE = 48;
    public static final int MEDIUM_SIZE = 64;
    public static final int LARGE_SIZE = 96;

    public CellWidget(FieldPanel parent) {
        super();
        setMaximumSize(new Dimension(128, 128));
        setPreferredSize(new Dimension(64, 64));
        setMinimumSize(new Dimension(48, 48));
        setLayout(new MigLayout("gap 0, nogrid"));
        this.parent = parent;
    }

    public FieldPanel getParent() {
        return parent;
    }

    public void addItem(CellItemWidget item) {
        if (item.getCell() == this || item.getCell() == null) {
            Component[] components = getComponents();
            for (Component cmp : components) {
                remove(cmp);
            }
            add(item, "pos 0 0, w 100%, h 100%");
            for (Component cmp : components) {
                add(cmp, "pos 0 0, w 100%, h 100%");
            }
            item.setCell(this);
        }
    }

    public void removeItem(CellItemWidget item) {
        item.setCell(null);
        remove(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (CellWidget.icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("cell.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        g.drawImage(CellWidget.icon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
