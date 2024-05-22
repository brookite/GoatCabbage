package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;
import brookite.games.goatcabbage.model.utils.MagneticPole;

import java.util.HashMap;
import java.util.Map;

public class MagnetBox extends MagneticBox {
    private Map<MagneticPole, Direction> pole;
    private boolean topRightRotate;

    public MagnetBox(Direction northPoleDirection, boolean topOrRightDirection) {
        pole = new HashMap<>();
        pole.put(MagneticPole.NORTH, northPoleDirection);
        pole.put(MagneticPole.SOUTH, northPoleDirection.opposite());
        topRightRotate = topOrRightDirection;
    }

    public Direction getMagnetDirection() {
        if (pole.get(MagneticPole.NORTH).equals(Direction.EAST) || pole.get(MagneticPole.NORTH).equals(Direction.WEST)) {
            return topRightRotate ? Direction.NORTH : Direction.SOUTH;
        } else {
            return topRightRotate ? Direction.EAST : Direction.WEST;
        }
    }

    public Map<MagneticPole, Direction> getPole() {
        return new HashMap<>(pole);
    }

    public MagneticPole getMagneticPoleByDirection(Direction direction) {
        for (MagneticPole mp : pole.keySet()) {
            if (pole.get(mp).equals(direction)) {
                return mp;
            }
        }
        return null;
    }

    @Override
    protected MagnetInteraction canInteract(Direction direction) {
        Cell neighbourCell = cell.neighbour(direction);
        if (neighbourCell.getSolidEntity().isEmpty()) {
            // проверка соседней ячейки
            Cell neighbourForEmptyCell = neighbourCell.neighbour(direction);
            if (neighbourForEmptyCell.hasSolidEntity()) {
                Entity entity = neighbourForEmptyCell.getSolidEntity().get();
                if (entity instanceof MagnetBox box) {
                    MagneticPole otherPole = box.getMagneticPoleByDirection(direction.opposite());
                    MagneticPole selfPole = getMagneticPoleByDirection(direction);
                    if (selfPole != null && otherPole != null && !otherPole.isOpposite(selfPole)) {
                        return MagnetInteraction.REPULSION;
                    } else {
                        // Случай, если у магнитов полюса параллельны
                        Direction newDirection = direction.clockwise();
                        MagneticPole parallelOtherPole = box.getMagneticPoleByDirection(newDirection);
                        MagneticPole parallelSelfPole = getMagneticPoleByDirection(newDirection);
                        if (parallelOtherPole != null && parallelSelfPole != null &&
                                !parallelSelfPole.isOpposite(parallelOtherPole)) {
                            return MagnetInteraction.REPULSION;
                        }
                    }
                } else if (entity instanceof MagneticBox box) {
                    return box.canInteract(direction.opposite()).equals(MagnetInteraction.REPULSION) ? MagnetInteraction.REPULSION : MagnetInteraction.NONE;
                }
            }
            return MagnetInteraction.NONE;
        }
        Entity entity = neighbourCell.getSolidEntity().get();
        if (entity instanceof MagnetBox box) {
            MagneticPole selfPole = getMagneticPoleByDirection(direction);
            MagneticPole otherPole = box.getMagneticPoleByDirection(direction.opposite());
            if (selfPole != null && otherPole != null) {
                return selfPole.isOpposite(otherPole) ? MagnetInteraction.ATTRACTION : MagnetInteraction.REPULSION;
            } else if (selfPole == null && otherPole == null) {
                // Случай, если у магнитов полюса параллельны
                Direction newDirection = direction.clockwise();
                MagneticPole parallelOtherPole = box.getMagneticPoleByDirection(newDirection);
                MagneticPole parallelSelfPole = getMagneticPoleByDirection(newDirection);
                if (parallelOtherPole != null && parallelOtherPole.isOpposite(parallelSelfPole)) {
                    if (getMagnetDirection().isOpposite(box.getMagnetDirection())) {
                        return MagnetInteraction.NONE;
                    }
                    return MagnetInteraction.ATTRACTION;
                } else if (parallelOtherPole != null && parallelSelfPole != null) {
                    return MagnetInteraction.REPULSION;
                }
                return MagnetInteraction.NONE;
            } else {
                return MagnetInteraction.ATTRACTION;
            }
        } else if (entity instanceof MagneticBox box) {
            return box.canInteract(direction.opposite());
        }
        return MagnetInteraction.NONE;
    }
}
