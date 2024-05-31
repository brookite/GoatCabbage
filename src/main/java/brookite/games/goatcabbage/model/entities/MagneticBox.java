package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.entities.hookable.HookableBox;
import brookite.games.goatcabbage.model.entities.hookable.HookedBoxChain;
import brookite.games.goatcabbage.model.events.MagnetInteractEvent;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;

public abstract class MagneticBox extends HookableBox {
    protected void fireInteractEvent(MagnetInteraction interaction, Direction direction) {
        fireActionEvent(new MagnetInteractEvent(this, direction, interaction));
    }

    public MagnetInteraction interact(Direction direction) {
        MagnetInteraction interaction = canInteract(direction);
        if (interaction.equals(MagnetInteraction.ATTRACTION)) {
            boolean hooked = hook(direction);
            if (hooked) {
                fireInteractEvent(interaction, direction);
            }
        }
        return interaction;
    }

    @Override
    protected boolean move(Direction direction) {
        boolean result = super.move(direction);
        if (result) {
            for (HookableBox box : new HookedBoxChain(this).getFigureItems()) {
                for (Direction dir : Direction.values()) {
                    if (box instanceof MagneticBox mb) {
                        mb.interact(dir);
                    }
                }
            }
        }
        return result;
    }

    protected abstract MagnetInteraction canInteract(Direction direction, boolean excludeInteractionWithHooked);

    protected MagnetInteraction canInteract(Direction direction) {
        return canInteract(direction, false);
    }

    protected boolean canMove(Direction direction) {
        boolean result = super.canMove(direction);
        HookedBoxChain chain = new HookedBoxChain(this);
        for (HookableBox item : chain.getFigureItems()) {
            MagneticBox box = (MagneticBox) item;
            result &= box.canInteract(direction, true) != MagnetInteraction.REPULSION;
        }
        return result;
    }

    @Override
    protected boolean canHook(Direction direction) {
        return canInteract(direction).equals(MagnetInteraction.ATTRACTION);
    }
}
