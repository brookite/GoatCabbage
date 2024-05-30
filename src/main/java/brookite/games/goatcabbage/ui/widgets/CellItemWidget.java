package brookite.games.goatcabbage.ui.widgets;

import javax.swing.*;
import java.awt.*;

public abstract class CellItemWidget extends JPanel {
    private CellWidget parent = null;

    public abstract ImageIcon getSourceIcon();

    public CellItemWidget() {
        setOpaque(false);
        setFocusable(true);
    }

    public CellWidget getCell() {
        return parent;
    }

    public void setCell(CellWidget parent) {
        this.parent = parent;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(getSourceIcon().getImage(), 0, 0, parent.getWidth(), parent.getHeight(), this);
    }
}
