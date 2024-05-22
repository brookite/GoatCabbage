package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;

public class MetalBox extends MagneticBox {
    @Override
    protected MagnetInteraction canInteract(Direction direction) {
        Cell neighbourCell = cell.neighbour(direction);
        if (neighbourCell.getSolidEntity().isEmpty()) {
            return MagnetInteraction.NONE;
        }
        Entity entity = neighbourCell.getSolidEntity().get();
        if (entity instanceof MagnetBox box) {
            return box.getMagneticPoleByDirection(direction.opposite()) != null || box.getMagnetDirection().equals(direction.opposite()) ? MagnetInteraction.ATTRACTION : MagnetInteraction.NONE;
        }
        return MagnetInteraction.NONE;
    }
}
