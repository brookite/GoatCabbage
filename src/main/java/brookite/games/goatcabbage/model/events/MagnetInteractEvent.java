package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.entities.MagneticBox;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;

public class MagnetInteractEvent extends ActionEvent {
    protected final MagneticBox actor;
    protected final Direction direction;
    protected final MagnetInteraction interaction;

    public MagnetInteractEvent(MagneticBox source, Direction direction, MagnetInteraction interaction) {
        super(source);
        this.actor = source;
        this.direction = direction;
        this.interaction = interaction;
    }

    public MagneticBox getActor() {
        return actor;
    }

    public Direction getDirection() {
        return direction;
    }

    public MagnetInteraction getInteraction() {
        return interaction;
    }
}
