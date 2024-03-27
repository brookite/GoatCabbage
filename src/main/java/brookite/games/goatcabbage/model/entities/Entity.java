package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;

public abstract class Entity {

	protected Cell cell;

	public boolean isSolid() {
		return true;
	}

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

	public Cell getCell() {
		return cell;
	}

}
