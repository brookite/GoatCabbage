package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.events.EntityMoveActionListener;
import brookite.games.goatcabbage.model.utils.Direction;

public interface Movable {

	boolean move(Direction direction);
	boolean canMove(Direction direction);
	void removeEntityActionListener(EntityMoveActionListener listener);
	void addMoveEntityActionListener(EntityMoveActionListener listener);
    boolean hasMoveEntityActionListeners();
}
