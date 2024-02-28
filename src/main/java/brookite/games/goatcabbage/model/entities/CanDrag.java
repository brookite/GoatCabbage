package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public interface CanDrag {

	public abstract boolean canDrag(Direction direction);

	public abstract void startDrag(Direction direction);

	public abstract void stopDrag();

}
