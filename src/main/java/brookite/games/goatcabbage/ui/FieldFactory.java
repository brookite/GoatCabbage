package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;

import java.io.IOException;

public class FieldFactory {
    public static FieldPanel fromLevel(LevelGameEnvironment level) {
        FieldPanel field = new FieldPanel();
        try {
            if (level.getUIProperties() != null) {
                field.changeBackground(ImageLoader.loadAsImageIcon(level.getUIProperties().getLevelCellBackground()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

    public static FieldPanel empty() {
        return new FieldPanel();
    }
}
