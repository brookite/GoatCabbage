package brookite.games.goatcabbage.model.events;

import java.util.EventObject;
import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;


public class CellEvent extends EventObject {
    private Cell target;
    private Entity actor;

    public CellEvent(Cell cell, Entity entity) {
        super(cell);
        this.target = cell;
        this.actor = entity;
    }


    public Entity getActor() {
        return actor;
    }

    public Cell getTarget() {
        return target;
    }
}
