package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class CabbageWidget extends EntityWidget {
    private static ImageIcon icon;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("cabbage.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CabbageWidget(Cabbage entity) {
        super(entity);
    }

    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("box.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }
}