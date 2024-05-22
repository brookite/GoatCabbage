package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.entities.*;
import brookite.games.goatcabbage.ui.widgets.*;

public class WidgetFactory {
    public static EntityWidget placeEntityWidget(Entity modelEntity, FieldPanel field) {
        EntityWidget entityWidget;
        if (modelEntity instanceof SimpleBox) {
            entityWidget = new BoxWidget((Box) modelEntity);
        } else if (modelEntity instanceof Wall) {
            entityWidget = new WallWidget((Wall) modelEntity);
        } else if (modelEntity instanceof Cabbage) {
            entityWidget = new CabbageWidget((Cabbage) modelEntity);
        } else if (modelEntity instanceof Goat) {
            entityWidget = new GoatWidget((Goat) modelEntity);
        } else if (modelEntity instanceof MagnetBox) {
            entityWidget = new MagnetBoxWidget((MagnetBox) modelEntity);
        } else if (modelEntity instanceof MetalBox) {
            entityWidget = new MetalBoxWidget((MetalBox) modelEntity);
        } else {
            throw new IllegalArgumentException("Неизвестный тип сущности");
        }
        field.cellAt(modelEntity.getCell().position()).addItem(entityWidget);
        return entityWidget;
    }
}
