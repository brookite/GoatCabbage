package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class GoatWidget extends EntityWidget {
    public GoatWidget(CellWidget parent, Goat goat) {
        super(parent, goat);
    }

    @Override
    public ImageIcon getSourceIcon() {
        try {
            return ImageLoader.loadAsImageIcon("goat.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
