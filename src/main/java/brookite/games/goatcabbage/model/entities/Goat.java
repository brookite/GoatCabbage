package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
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
		if (canDrag(direction)) {
			startDrag(direction);
		}

		if (!canMove(direction)) {
			return false;
		}

		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);

		if (isDragged()) {
			Entity draggedEntity = (Entity) dragged;
			Cell neighbourForDraggedCell = neighbourCell.getOwner().neighbour(neighbourCell, direction);
			if (!neighbourForDraggedCell.canPutEntity(draggedEntity) && !neighbourForDraggedCell.isWall(direction.opposite())) {
				return false;
			}
			neighbourCell.removeEntity(draggedEntity);
		}

		decreaseStep();
		neighbourCell.putEntity(this);

		return true;
	}

	@Override
	public boolean canMove(Direction direction) {
		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
		return neighbourCell != null && !neighbourCell.isWall(direction.opposite()) && neighbourCell.canPutEntity(this);
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#canDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean canDrag(Direction direction) {
		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
		if (neighbourCell == null) {
			return false;
		}
		return neighbourCell.getEntities().stream().anyMatch((Entity entity) -> entity instanceof Dragable);
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#startDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean startDrag(Direction direction) {
		if (canDrag(direction)) {
			Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
            this.dragged = (Dragable) neighbourCell.getEntities().stream().filter((Entity entity) -> entity instanceof Dragable).findFirst().get();
			return true;
		}
		return false;
	}

	@Override
	public boolean isDragged() {
		return dragged != null;
	}

	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#stopDrag()
	 */
	@Override
	public void stopDrag() {
		dragged = null;
	}

}
