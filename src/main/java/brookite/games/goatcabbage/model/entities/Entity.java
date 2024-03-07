package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;

public abstract class Entity {

	protected Cell cell;

	protected boolean _isSolid;

	public boolean isSolid() {
		return _isSolid;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return cell;
	}

}
