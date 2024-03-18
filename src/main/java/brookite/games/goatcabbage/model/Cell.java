package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.Goat;
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


    private int _getWallArrayIndexByDirection(Direction direction) {
        int index = 0;
        if (direction.equals(Direction.east())) {
            index = 3;
        } else if (direction.equals(Direction.south())) {
            index = 2;
        } else if (direction.equals(Direction.west())) {
            index = 1;
        } else if (direction.equals(Direction.north())) {
            index = 0;
        }
        return index;
    }

    public void setWall(Direction direction, boolean set) {
        _walls[_getWallArrayIndexByDirection(direction)] = set;
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
        this.owner = owner;
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

    public boolean isWall(Direction direction) {
        return _walls[_getWallArrayIndexByDirection(direction)];
    }

    public boolean hasSolidEntity() {
        return getSolidEntity().isPresent();
    }

    public boolean isEmpty() {
        return entities.isEmpty();
    }

    public boolean canPutEntity(Entity entity) {
        return (!entity.isSolid() || !hasSolidEntity()) && !hasEntity(entity);
    }

    public boolean putEntity(Entity entity) {
        if (canPutEntity(entity)) {
            entities.add(entity);
            entity.setCell(this);

            if (owner != null && entity instanceof Goat) {
                owner.setGoat((Goat) entity);
            }
            if (owner != null && entity instanceof Cabbage) {
                owner.setCabbage((Cabbage) entity);
            }

            return true;
        }
        return false;
    }

    public boolean hasStateListeners() {
        return !_listeners.isEmpty();
    }

    public boolean hasEntity(Entity entity) {
        return entities.contains(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}

