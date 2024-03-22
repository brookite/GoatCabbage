package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;

public interface CellStateListener {

	public void onEntitySteppedIn(CellEvent event);

	public void onEntitySteppedOut(CellEvent event);

}
