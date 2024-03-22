package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.entities.Entity;

import java.util.EventObject;

public class GameResultEvent extends EventObject {
	private final boolean isWin;

	public GameResultEvent(Entity winner, boolean win) {
        super(winner);
        isWin = win;
	}

	public boolean isWin() {
		return isWin;
	}

}
