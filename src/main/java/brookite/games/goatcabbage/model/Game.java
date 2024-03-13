package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.events.CellStateListener;
import brookite.games.goatcabbage.model.events.GameResult;
import brookite.games.goatcabbage.model.events.GameStateListener;
import brookite.games.goatcabbage.model.levels.GameEnvironment;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Paddock paddock;
    private GameEnvironment[] environments;
    private int currentEnvironment = 0;

    private ArrayList<GameStateListener> _listeners = new ArrayList<>();

    public void start() {

    }

    private void fireGameFinished(boolean isWin, boolean nextGame) {
        for (GameStateListener listener : _listeners) {
            listener.onGameFinished(new GameResult(isWin, nextGame));
        }
    }

    public void addGameStateListener(GameStateListener listener) {
        _listeners.add(listener);
    }

    public void removeGameStateListener(GameStateListener listener) {
        _listeners.add(listener);
    }

    public boolean hasGameStateListeners() {return !_listeners.isEmpty();}

    public void nextEnvironment() {
        currentEnvironment = (currentEnvironment + 1) % this.environments.length;
    }

    public void setEnvironments(GameEnvironment[] environments) {
        this.environments = environments;
        currentEnvironment = 0;
    }

    public GameEnvironment getCurrentEnvironment() {
        return environments[currentEnvironment];
    }

    public List<GameEnvironment> getEnvironments() {
        return List.of(environments);
    }

    public boolean started() {
        return false;
    }

    public void finish() {

    }

    public Paddock getPaddock() {
        return paddock;
    }
}
