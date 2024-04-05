package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.utils.Direction;

public class MoveEvent extends ActionEvent{
    protected final Entity actor;
    protected final Cell previousPosition;
    protected final Cell newPosition;

    protected final Direction direction;

    public MoveEvent(Entity actor, Cell previousPosition, Cell newPosition, Direction direction) {
        super(actor);
        this.actor = actor;
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
        this.direction = direction;
    }

    public Entity getActor() {
        return actor;
    }

    public Cell getNewPosition() {
        return newPosition;
    }

    public Cell getPreviousPosition() {
        return previousPosition;
    }

    public Direction getDirection() {
        return direction;
    }
}
