package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.EatEvent;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.Optional;

public class Goat extends MovableEntity implements CanDrag, Solid {
	public Goat() {
		addActionListener(new ActionListener() {
			@Override
			public void onActionPerformed(ActionEvent event) {
				if (event instanceof MoveEvent moveEvent) {
					Cell cell = moveEvent.getNewPosition();
					for (Entity entity : cell.getPassableEntities()) {
						if (entity instanceof Cabbage) {
							eat((Cabbage) entity);
						}
					}
				}
			}
		});
	}
	private int stepAmount;

	private Entity dragged;
	private Direction dragDirection;

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
		this();
		this.stepAmount = stepAmount;
	}

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
		fireActionEvent(new EatEvent(this, cabbage));
	}

	@Override
	public boolean move(Direction direction) {
		if (!canMove(direction)) {
			return false;
		}

		if (canTake(direction)) {
			take(direction);
			return false;
		}

		Cell oldCell = this.cell;
		Cell neighbourCell = cell.neighbour(direction);

		decreaseStep();
		cell.removeEntity(this);

		if (isTaken() && (direction == dragDirection || direction == dragDirection.opposite())) {
			drag(direction);
		}

		neighbourCell.putEntity(this);

		fireEntityMoved(this, oldCell, direction);

		return true;
	}

	private void fireEntityMoved(Goat goat, Cell oldCell, Direction direction) {
		fireActionEvent(new MoveEvent(goat, oldCell, this.cell, direction));
	}

	@Override
	public boolean canMove(Direction direction) {
		Cell neighbourCell = cell.neighbour(direction);

		if (neighbourCell == null) {
			return false;
		}

		if (neighbourCell.hasDraggableEntity()) {
			Cell neighbourForDraggedCell = neighbourCell.neighbour(direction);
			if (neighbourForDraggedCell != null) {
				return neighbourForDraggedCell.canPutEntity(neighbourCell.getDraggableEntities().get(0));
			}
			return false;
		}

		return neighbourCell.canPutEntity(this);
	}


	@Override
	public boolean canTake(Direction direction) {
		Cell neighbourCell = cell.neighbour(direction);
		if (neighbourCell == null || dragged != null) {
			return false;
		}
		return neighbourCell.getEntities().stream().anyMatch((Entity entity) -> entity instanceof Draggable);
	}

	@Override
	public boolean take(Direction direction) {
		if (!canTake(direction)) {
			return false;
		}
		Cell neighbourCell = cell.neighbour(direction);
		Optional<Entity> draggableEntity = neighbourCell.getEntities().stream().filter((Entity entity) -> entity instanceof Draggable).findFirst();;
		dragged = draggableEntity.orElse(null);
		return dragged != null;
	}

	private Entity getDraggedEntity() {
		return dragged;
	}

	@Override
	public void drag(Direction newDirection) {
		Cell cell = dragged.getCell();
		Cell neighbourCell = cell.neighbour(newDirection);
		if (neighbourCell.canPutEntity(dragged)) {
			cell.removeEntity(dragged);
			neighbourCell.putEntity(dragged);
		}
	}

	@Override
	public boolean isTaken() {
		return dragged != null;
	}

}
