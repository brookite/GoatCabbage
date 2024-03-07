package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.events.GameStateListener;
import brookite.games.goatcabbage.model.levels.GameEnvironment;

public class Game {
    private Paddock paddock;

    private GameEnvironment[] environments;

    private int currentEnvironment;

    private GameEnvironment gameEnvironment;

    public void start() {

    }

    private void fireGameFinished(boolean isWin) {

    }

    public void addGameStateListener(GameStateListener listener) {

    }

    public void nextEnvironment() {

    }

    public boolean started() {
        return false;
    }

    public void finish() {

    }

}
