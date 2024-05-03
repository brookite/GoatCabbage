package brookite.games.goatcabbage.ui.widgets;

import com.formdev.flatlaf.util.ScaledImageIcon;

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
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        super.paintComponent(g);
        ScaledImageIcon scaledIcon = new ScaledImageIcon(getSourceIcon(), parent.getWidth(), parent.getHeight());
        scaledIcon.paintIcon(this, g, 0, 0);
    }
}
