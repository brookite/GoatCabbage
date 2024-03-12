package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.events.CellStateListener;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.entities.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cell {

    /**
     * Direction position format in array: [NORTH, WEST, SOUTH, EAST]
     */
    private boolean[] _walls = new boolean[4];

    private ArrayList<Entity> entities;

    private Paddock owner;

    private ArrayList<CellStateListener> _listeners = new ArrayList<>();

    public void setWall(Direction position, boolean set) {

    }

    protected void fireEntitySteppedIn(Entity entity) {
        for (CellStateListener listener : _listeners) {
            listener.onEntitySteppedIn(this, entity);
        }
    }

    protected void fireEntitySteppedOut(Entity entity) {
        for (CellStateListener listener : _listeners) {
            listener.onEntitySteppedOut(this, entity);
        }
    }

    public Cell(Paddock owner) {
        this.owner = owner;
        entities = new ArrayList<>();
    }

    public Paddock getOwner() {
        return owner;
    }

    void setOwner(Paddock owner) {

    }

    public List<Entity> getEntities() {
        return new ArrayList<Entity>(entities);
    }

    public Optional<Entity> getSolidEntity() {
        return entities.stream().filter(Entity::isSolid).findFirst();
    }

    public List<Entity> getPassableEntities() {
        return entities.stream().filter(Predicate.not(Entity::isSolid)).collect(Collectors.toList());
    }

    public void addStateListener(CellStateListener listener) {
        if (!_listeners.contains(listener)) {
            _listeners.add(listener);
        }
    }

    public void removeStateListener(CellStateListener listener) {
        _listeners.remove(listener);
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

    public boolean putEntity(Entity entity) {
        entities.add(entity);
        return true;
    }

    public boolean hasStateListeners() {
        return !_listeners.isEmpty();
    }
}

