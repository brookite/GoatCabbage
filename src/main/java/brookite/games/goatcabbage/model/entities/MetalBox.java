package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.hookable.HookedBoxChain;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;

public class MetalBox extends MagneticBox {
    @Override
    protected MagnetInteraction canInteract(Direction direction, boolean excludeInteractionWithHooked) {
        Cell neighbourCell = cell.neighbour(direction);
        HookedBoxChain thisChain = new HookedBoxChain(this);
        if (neighbourCell == null || neighbourCell.getSolidEntity().isEmpty()) {
            return MagnetInteraction.NONE;
        }
        Entity entity = neighbourCell.getSolidEntity().get();
        if (entity instanceof MagnetBox box) {
            if (thisChain.isInOneChain(box) && excludeInteractionWithHooked) {
                return MagnetInteraction.NONE;
            }
            return box.getMagneticPoleByDirection(direction.opposite()) != null || box.getMagnetDirection().equals(direction.opposite()) ? MagnetInteraction.ATTRACTION : MagnetInteraction.NONE;
        }
        return MagnetInteraction.NONE;
    }
}
