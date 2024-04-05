package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;

public abstract class Entity {

	protected Cell cell;
	private ArrayList<ActionListener> _listeners = new ArrayList<>();

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
		if (!_listeners.contains(listener)) {
			_listeners.add(listener);
		}
	}

	public boolean hasActionListeners() {
		return !_listeners.isEmpty();
	}

	public void removeActionListener(ActionListener listener) {
		_listeners.remove(listener);
	}

	protected void fireActionEvent(ActionEvent event) {
		for (ActionListener listener : _listeners) {
			listener.onActionPerformed(event);
		}
	}

	public Cell getCell() {
		return cell;
	}

}
