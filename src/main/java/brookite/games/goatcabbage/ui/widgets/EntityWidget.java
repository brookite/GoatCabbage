package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Entity;

public abstract class EntityWidget extends CellItemWidget {
    protected Entity entity;

    public EntityWidget(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getModelEntity() {
        return entity;
    }
}
