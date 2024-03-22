package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.events.EatActionListener;
import brookite.games.goatcabbage.model.events.EatEvent;
import brookite.games.goatcabbage.model.events.EntityMoveActionListener;
import brookite.games.goatcabbage.model.events.MoveActionEvent;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;

public class Goat extends Entity implements Movable, CanDrag, Eating {

	protected ArrayList<EntityMoveActionListener> _moveListeners = new ArrayList<>();
	protected ArrayList<EatActionListener> _eatListeners = new ArrayList<>();

	public Goat() {
		addMoveEntityActionListener(new EntityMoveActionListener() {
			@Override
			public void entityMoved(MoveActionEvent event) {
				Cell cell = event.getNewPosition();
				for (Entity entity : cell.getPassableEntities()) {
					if (entity instanceof Cabbage) {
						eat((Cabbage) entity);
					}
				}
			}
		});
	}
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

	@Override
	public boolean eat(Entity entity) {
		if (entity instanceof Cabbage) {
			Cabbage cabbage = (Cabbage) entity;
			if (cabbage.getCell().equals(getCell())) {
				cabbage.getCell().removeEntity(cabbage);
				fireCabbageEaten(cabbage);
			}
			fireCabbageEaten(cabbage);
			return true;
		}
		return false;
	}

	protected void fireCabbageEaten(Cabbage cabbage) {
		for (EatActionListener listener : _eatListeners) {
			listener.onEntityEaten(new EatEvent(this, cabbage));
		}
	}

	@Override
	public void addEatEventListener(EatActionListener listener) {
		_eatListeners.add(listener);
	}

	@Override
	public void removeEatEventListener(EatActionListener listener) {
		_eatListeners.remove(listener);
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.Movable#move(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean move(Direction direction) {
		Cell oldCell = this.cell;

		if (canDrag(direction)) {
			startDrag(direction);
			return false;
		}

		if (!canMove(direction)) {
			return false;
		}

		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);

		Cell neighbourForDraggedCell = null;
		Entity draggedEntity = getDraggedEntity();
		if (isDragged()) {
			neighbourForDraggedCell = neighbourCell.getOwner().neighbour(neighbourCell, direction);
			neighbourCell.removeEntity(draggedEntity);
		}

		decreaseStep();
		neighbourCell.putEntity(this);

		if (neighbourForDraggedCell != null) {
			neighbourForDraggedCell.putEntity(draggedEntity);
			stopDrag();
		}

		fireEntityMoved(this, oldCell, direction);

		return true;
	}

	@Override
	public boolean canMove(Direction direction) {
		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
		if (neighbourCell == null) {
			return false;
		} else if (isDragged() && neighbourCell.hasEntity(getDraggedEntity())) {
			Entity draggedEntity = getDraggedEntity();
			Cell neighbourForDraggedCell = neighbourCell.getOwner().neighbour(neighbourCell, direction);
			return !neighbourForDraggedCell.canPutEntity(draggedEntity)
					&& !neighbourForDraggedCell.isWall(direction.opposite())
					&& !neighbourCell.isWall(direction.opposite()) && hasSteps();
		} else {
			return neighbourCell.canPutEntity(this) && !neighbourCell.isWall(direction.opposite()) && hasSteps();
		}
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#canDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean canDrag(Direction direction) {
		Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
		if (neighbourCell == null || dragged != null) {
			return false;
		}
		return neighbourCell.getEntities().stream().anyMatch((Entity entity) -> entity instanceof Dragable);
	}


	private void setDragged(Dragable dragable) {
		dragged = dragable;
	}

	private Entity getDraggedEntity() {
		return (Entity) dragged;
	}


	/**
	 * @see brookite.games.goatcabbage.model.entities.CanDrag#startDrag(brookite.games.goatcabbage.model.utils.Direction)
	 */
	@Override
	public boolean startDrag(Direction direction) {
		if (canDrag(direction)) {
			Cell neighbourCell = cell.getOwner().neighbour(cell, direction);
            setDragged((Dragable) neighbourCell.getEntities().stream().filter((Entity entity) -> entity instanceof Dragable).findFirst().get());
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

	@Override
	public void addMoveEntityActionListener(EntityMoveActionListener listener) {
		_moveListeners.add(listener);
	}

	@Override
	public void removeEntityActionListener(EntityMoveActionListener listener) {
		_moveListeners.remove(listener);
	}

	protected void fireEntityMoved(Entity target, Cell oldCell, Direction direction) {
		MoveActionEvent event = new MoveActionEvent(this, oldCell, cell, direction);
		for (EntityMoveActionListener listener : _moveListeners) {
			listener.entityMoved(event);
		}
	}

}
