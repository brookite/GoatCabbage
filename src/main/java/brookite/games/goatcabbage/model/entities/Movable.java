package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public interface Movable {

	public abstract boolean move(Direction direction);
	public abstract boolean canMove(Direction direction);

}
