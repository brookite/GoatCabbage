package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class WallWidget extends EntityWidget {
    public WallWidget(CellWidget cell, Box entity) {
        super(cell, entity);
    }

    @Override
    public ImageIcon getSourceIcon() {
        try {
            return ImageLoader.loadAsImageIcon("fence2.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}