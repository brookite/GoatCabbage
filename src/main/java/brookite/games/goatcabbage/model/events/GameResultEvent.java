package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.entities.Entity;

import java.util.EventObject;
import java.util.Optional;

public class GameResultEvent extends EventObject {
	private final boolean isWin;

	public GameResultEvent(Entity winner, boolean win) {
        super(Optional.ofNullable(winner));
        isWin = win;
	}

	public boolean isWin() {
		return isWin;
	}
}
