package brookite.games.goatcabbage.ui.widgets;

import com.formdev.flatlaf.util.ScaledImageIcon;

import javax.swing.*;
import java.awt.*;

public abstract class CellItemWidget extends JPanel {
    private final CellWidget parent;

    public abstract ImageIcon getSourceIcon();

    public CellItemWidget(CellWidget parent) {
        this.parent = parent;
    }

    @Override
    protected void paintComponent(Graphics g) {
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        super.paintComponent(g);
        //ScaledImageIcon scaledIcon = new ScaledImageIcon(getSourceIcon(), parent.getWidth(), parent.getHeight());
        //scaledIcon.paintIcon(this, g, 0, 0);
        g.drawImage(getSourceIcon().getImage(), 0, 0, parent.getWidth(), parent.getHeight(), this);
    }
}
