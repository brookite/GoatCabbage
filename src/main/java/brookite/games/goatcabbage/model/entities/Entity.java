package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;

public abstract class Entity {

	protected Cell cell;

	protected boolean _isSolid;

	public boolean isSolid() {
		return _isSolid;
	}

	public void setCell(Cell cell) {
		if (this.cell == cell) {
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
		}


	}

	public Cell getCell() {
		return cell;
	}

}
