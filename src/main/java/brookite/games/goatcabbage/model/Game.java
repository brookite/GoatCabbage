package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.*;
import brookite.games.goatcabbage.model.levels.GameEnvironment;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Paddock paddock;
    private GameEnvironment[] environments;
    private int currentEnvironment = 0;
    private boolean isGameStarted = false;

    private final ArrayList<GameStateListener> _listeners = new ArrayList<>();

    public void start() {
        GameEnvironment env = getCurrentEnvironment();
        this.paddock = env.create();

        paddock.getGoat().addActionListener(event -> {
            if (event instanceof EatEvent eatEvent) {
                if (eatEvent.getVictim() instanceof Cabbage) {
                    fireGameFinished(eatEvent.getEating(), true);
                }
            }
        });
        paddock.getGoat().addActionListener(event -> {
            if (event instanceof MoveEvent moveEvent) {
                Goat goat = (Goat) moveEvent.getActor();
                if (!goat.hasSteps()) {
                    fireGameFinished(null, false);
                }
            }
        });
        isGameStarted = true;

    }

    private void fireGameFinished(Entity winner, boolean isWin) {
        for (GameStateListener listener : _listeners) {
            listener.onGameFinished(new GameResultEvent(winner, isWin));
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

    public boolean setCurrentEnvironment(GameEnvironment newCurrent) {
        for (int i = 0; i < environments.length; i++) {
            if (newCurrent == environments[i]) {
                currentEnvironment = i;
                return true;
            }
        }
        return false;
    }

    public List<GameEnvironment> getEnvironments() {
        return List.of(environments);
    }

    public boolean started() {
        return isGameStarted;
    }

    public void finish() {
        isGameStarted = false;
    }

    public Paddock getPaddock() {
        return paddock;
    }
}
