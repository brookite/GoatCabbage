package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.events.CellStateListener;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.entities.Entity;

import java.util.ArrayList;

public class Cell {

    private ArrayList<Entity> entities;

    private Paddock owner;

    public void setWall(Direction position, boolean set) {

    }

    public void addStateListener(CellStateListener listener) {

    }

    public boolean isWall(Direction position) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean canPutEntity(Entity entity) {
        return false;
    }

}

