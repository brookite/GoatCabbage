package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;

public abstract class MovableEntity extends Entity {
    protected boolean move(Direction direction) {
        if (!canMove(direction)) {
            return false;
        }

        Cell oldCell = this.cell;
        Cell neighbourCell = cell.neighbour(direction);
        cell.removeEntity(this);

        neighbourCell.putEntity(this);

        fireEntityMoved(this, oldCell, direction);

        return true;
    }

    protected void fireEntityMoved(Entity entity, Cell oldCell, Direction direction) {
        fireActionEvent(new MoveEvent(entity, oldCell, this.cell, direction));
    }

    protected boolean canMove(Direction direction) {
        Cell neighbourCell = cell.neighbour(direction);

        if (neighbourCell == null) {
            return false;
        }

        return neighbourCell.canPutEntity(this);
    }
}
