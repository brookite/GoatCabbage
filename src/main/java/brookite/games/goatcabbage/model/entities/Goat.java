package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public class Goat extends Entity implements Movable, CanDrag {

	private int stepAmount;

	private Dragable dragged;

	private void decreaseStep() {

	}

	public boolean hasSteps() {
		return false;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.Movable#move(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean move(Direction direction) {
		return false;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#canDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean canDrag(Direction direction) {
		return false;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#startDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public void startDrag(Direction direction) {

	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#stopDrag()
	 */
	@Override
	public void stopDrag() {

	}

}
