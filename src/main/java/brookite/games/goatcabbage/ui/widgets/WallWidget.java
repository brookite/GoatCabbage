package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class WallWidget extends EntityWidget {
    public WallWidget(Wall entity) {
        super(entity);
    }

    private static final ImageIcon icon;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("fence2.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageIcon getSourceIcon() {
        return icon;
    }
}