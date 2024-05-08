package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class WallWidget extends EntityWidget {
    public WallWidget(Wall entity) {
        super(entity);
    }

    private static ImageIcon icon;


    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("fence2.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }
}