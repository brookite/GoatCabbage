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
		Cell neighbourCellBehind = cell.neighbour(direction.opposite());
		if (neighbourCell != null &&
				neighbourCell.hasSolidEntity() &&
				neighbourCell.getSolidEntity().get() instanceof Box box
		) {
            boolean dragResult = box.drag(this, direction);
			if (!dragResult) {
				return false;
			}
		}

		boolean canDragBack = false;

		if (neighbourCellBehind != null &&
				neighbourCellBehind.hasSolidEntity() &&
				neighbourCellBehind.getSolidEntity().get() instanceof Box box)
		{
			canDragBack = box.canDrag(this, direction);
		}

		if (!canMove(direction)) {
			return false;
		}

		cell.removeEntity(this);

		neighbourCell.putEntity(this);
		decreaseStep();

		fireEntityMoved(this, oldCell, direction);

		if (canDragBack) {
			Box box = (Box) neighbourCellBehind.getSolidEntity().get();
			box.drag(this, direction);
		}
		return true;
	}

	@Override
	protected boolean canMove(Direction direction) {
		return super.canMove(direction) && hasSteps();
	}

}
