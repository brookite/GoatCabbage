package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;

import java.util.ArrayList;

public abstract class Entity {

	protected Cell cell;
	private final ArrayList<ActionListener> listeners = new ArrayList<>();

	public void setCell(Cell cell) {
		if (cell == null || this.cell == cell) {
			this.cell = cell;
			return;
		}

		if (this.cell != null) {
			this.cell.removeEntity(this);
		}

		if (!cell.hasEntity(this)) {
			if (cell.putEntity(this)) {
				this.cell = cell;
			} else {
				this.cell.putEntity(this);
			}
		} else {
			this.cell = cell;
		}


	}

	public void addActionListener(ActionListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public boolean hasActionListeners() {
		return !listeners.isEmpty();
	}

	public void removeActionListener(ActionListener listener) {
		listeners.remove(listener);
	}

	protected void fireActionEvent(ActionEvent event) {
		for (ActionListener listener : listeners) {
			listener.onActionPerformed(event);
		}
	}

	public Cell getCell() {
		return cell;
	}

}
