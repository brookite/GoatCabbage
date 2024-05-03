package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.ui.utils.ImageLoader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CellWidget extends JPanel {
    static ImageIcon icon;
    private final FieldPanel parent;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("cell.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            add(item, "pos 0 0");
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

        if (CellWidget.icon != null) {
            g.drawImage(CellWidget.icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
