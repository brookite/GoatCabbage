package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;

public abstract class Entity {

	protected Cell cell;

	protected boolean _isSolid;

	public boolean isSolid() {
		return false;
	}

}
