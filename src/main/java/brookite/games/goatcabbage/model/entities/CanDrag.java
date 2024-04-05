package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public interface CanDrag {

	boolean canTake(Direction direction);

	boolean take(Direction direction);

	boolean isTaken();

	void drag(Direction newDirection);

}
