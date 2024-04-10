package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public class Box extends MovableEntity implements Solid {
    public boolean drag(Entity actor, Direction direction) {
        return false;
    }
}
