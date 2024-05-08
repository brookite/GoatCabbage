package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.data.Field;
import brookite.games.goatcabbage.ui.utils.EmptyLevel;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;

import java.io.IOException;

public class FieldFactory {
    public static FieldPanel fromEnvironment(GameEnvironment env) {
        FieldPanel field = new FieldPanel(env.create());
        try {
            if (env.getUIProperties() != null) {
                field.changeBackground(ImageLoader.loadAsImageIcon(env.getUIProperties().getLevelCellBackground()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

    public static FieldPanel fromGameModel(Game game) {
        FieldPanel field = new FieldPanel(game.getPaddock());
        GameEnvironment env = game.getCurrentEnvironment();
        try {
            if (env.getUIProperties() != null) {
                field.changeBackground(ImageLoader.loadAsImageIcon(env.getUIProperties().getLevelCellBackground()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

    public static FieldPanel empty() {
        return new FieldPanel(new EmptyLevel().create());
    }
}
