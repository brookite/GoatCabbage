package brookite.games.goatcabbage.ui.utils;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.data.Level;
import brookite.games.goatcabbage.model.levels.data.UIProperties;

public class EmptyLevel extends LevelGameEnvironment {
    public EmptyLevel() {
        super(null);
    }


    @Override
    public Paddock create() {
        return new Paddock(24, 10);
    }

    @Override
    public UIProperties getUIProperties() {
        return null;
    }
}
