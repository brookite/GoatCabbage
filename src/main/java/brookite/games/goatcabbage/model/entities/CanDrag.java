package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public interface CanDrag {

	boolean canDrag(Direction direction);

	boolean startDrag(Direction direction);

	boolean isDragged();

	void stopDrag();

}
