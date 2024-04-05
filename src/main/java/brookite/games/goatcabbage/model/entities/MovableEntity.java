package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public abstract class MovableEntity extends Entity{
    public boolean move(Direction direction) {
        return false;
    }

    public boolean canMove(Direction direction) {
        return false;
    }
}
