package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Entity;

public abstract class EntityWidget extends CellItemWidget {
    private Entity entity;

    public EntityWidget(CellWidget cell, Entity entity) {
        super(cell);
        this.entity = entity;
    }
}
