package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public class Box extends MovableEntity implements Solid {
    public boolean drag(Entity actor, Direction direction) {
        return super.move(direction);
    }

    public boolean canDrag(Entity actor, Direction direction) {
        return actor.getCell().isNeighbour(this.getCell());
    }
}
