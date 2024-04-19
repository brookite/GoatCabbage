package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.EatEvent;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;

public class Goat extends MovableEntity implements Solid {
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
		Cell oldCell = this.cell;
		Cell neighbourCell = cell.neighbour(direction);

		if (!canMove(direction)) {
			return false;
		}

		cell.removeEntity(this);

		neighbourCell.putEntity(this);
		decreaseStep();

		fireEntityMoved(this, oldCell, direction);
		return true;
	}

	protected boolean canPull(Direction direction) {
		Cell neighbourCellBehind = cell.neighbour(direction.opposite());
		Cell neighbourCell = cell.neighbour(direction);
		return neighbourCell != null && neighbourCellBehind != null &&
				neighbourCell.canPutEntity(this) && neighbourCellBehind.hasEntity(Box.class);
	}

	protected boolean canPush(Direction direction) {
		Cell neighbourCell = cell.neighbour(direction);
		if (neighbourCell != null && neighbourCell.hasEntity(Box.class)) {
			Cell neighbourForBoxCell = neighbourCell.neighbour(direction);
			return neighbourForBoxCell != null && neighbourForBoxCell.canPutEntity(neighbourCell.getSolidEntity().get());
		}
		return false;
	}

	public boolean movePull(Direction direction) {
		if (!canPull(direction)) {
			return false;
		}
		Cell neighbourCellBehind = cell.neighbour(direction.opposite());
		Box box = (Box) neighbourCellBehind.getSolidEntity().get();
		boolean result = move(direction);
		if (result) {
			return box.move(direction);
		}
		return false;
	}

	public boolean movePush(Direction direction) {
		if (!canPush(direction)) {
			return false;
		}
		Cell neighbourCell = cell.neighbour(direction);
		Box box = (Box) neighbourCell.getSolidEntity().get();
		boolean result = box.move(direction);
		if (result) {
			return move(direction);
		}
		return false;
	}

	@Override
	protected boolean canMove(Direction direction) {
		return super.canMove(direction) && hasSteps();
	}

}
