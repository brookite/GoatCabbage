package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.MetalBox;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class MetalBoxWidget extends MagneticBoxWidget {
    private static ImageIcon icon;

    public MetalBoxWidget(MetalBox entity) {
        super(entity);
    }

    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("metal_box.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }
}
