package brookite.games.goatcabbage.model;

import brookite.games.goatcabbage.model.entities.*;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cell {
    private ArrayList<Entity> entities;

    private Paddock owner;

    public Cell(Paddock owner) {
        setOwner(owner);
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
        return entities.stream().filter((Entity entity) -> entity instanceof Solid).findFirst();
    }

    public List<Entity> getPassableEntities() {
        return entities.stream().filter((Entity entity) -> !(entity instanceof Solid)).collect(Collectors.toList());
    }

    public boolean hasSolidEntity() {
        return getSolidEntity().isPresent();
    }

    public boolean isEmpty() {
        return entities.isEmpty();
    }

    public boolean canPutEntity(Entity entity) {
        return (!(entity instanceof Solid) || !hasSolidEntity()) && !hasEntity(entity);
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

    public boolean hasEntity(Entity entity) {
        return entities.contains(entity);
    }

    public boolean hasEntity(Class<? extends Entity> entityClass) {
        return entities.stream().anyMatch(entityClass::isInstance);
    }

    public boolean isWall() {
        if (hasSolidEntity()) {
            return this.getSolidEntity().get() instanceof Wall;
        }
        return false;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        entity.setCell(null);
    }

    public Cell neighbour(Direction direction) {
        return owner.findNeighbour(this, direction);
    }

    public boolean isNeighbour(Cell cell) {
        return owner.isNeighbours(this, cell);
    }

    public CellPosition position() {
        return owner.position(this);
    }

    public void clear() {
        for (Entity entity : entities) {
            entity.setCell(null);
        }
        entities = new ArrayList<>();
    }
}

