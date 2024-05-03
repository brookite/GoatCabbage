package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.entities.*;
import brookite.games.goatcabbage.ui.widgets.*;

public class WidgetFactory {
    public static EntityWidget placeEntityWidget(Entity modelEntity, FieldPanel field) {
        EntityWidget entityWidget;
        if (modelEntity instanceof Box) {
            entityWidget = new BoxWidget((Box) modelEntity);
        } else if (modelEntity instanceof Wall) {
            entityWidget = new WallWidget((Wall) modelEntity);
        } else if (modelEntity instanceof Cabbage) {
            entityWidget = new CabbageWidget((Cabbage) modelEntity);
        } else if (modelEntity instanceof Goat) {
            entityWidget = new GoatWidget((Goat) modelEntity);
        } else {
            throw new IllegalArgumentException("Неизвестный тип сущности");
        }
        field.cellAt(modelEntity.getCell().position()).addItem(entityWidget);
        return entityWidget;
    }
}
