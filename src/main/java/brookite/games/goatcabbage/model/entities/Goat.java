package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.utils.Direction;

public class Goat extends Entity implements Movable, CanDrag {

	private int stepAmount;

	private Dragable dragged;

	private boolean decreaseStep() {
		if (hasSteps()) {
			stepAmount--;
			return true;
		}
		return false;
	}

	public int getStepAmount() {
		return stepAmount;
	}

	public boolean hasSteps() {
		return stepAmount > 0;
	}


	public Goat(int stepAmount) {
		this.stepAmount = stepAmount;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.Movable#move(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean move(Direction direction) {
		return false;
	}

	@Override
	public boolean canMove(Direction direction) {
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
	public boolean startDrag(Direction direction) {
		return false;
	}

	@Override
	public boolean isDragged() {
		return false;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#stopDrag()
	 */
	@Override
	public void stopDrag() {

	}

}
