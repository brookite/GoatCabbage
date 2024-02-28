package brookite.games.goatcabbage.model.events;

public class GameResult {
	private final boolean isWin;
	private final boolean startNextEnvironment;

	public GameResult(boolean win, boolean nextGame) {
		isWin = win;
		startNextEnvironment = nextGame;
	}

	public boolean isWin() {
		return isWin;
	}

	public boolean isStartNextEnvironment() {
		return startNextEnvironment;
	}

}
