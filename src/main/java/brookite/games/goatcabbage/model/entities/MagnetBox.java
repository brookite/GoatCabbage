package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.hookable.HookedBoxChain;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;
import brookite.games.goatcabbage.model.utils.MagneticPole;

import java.util.HashMap;
import java.util.Map;

public class MagnetBox extends MagneticBox {
    private final Map<MagneticPole, Direction> pole;
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

    private MagnetInteraction checkRepulsion(Direction direction, boolean excludeInteractionWithHooked) {
        HookedBoxChain thisChain = new HookedBoxChain(this);
        Cell neighbourCell = cell.neighbour(direction);
        for (Direction neighbourDir : Direction.values()) {
            if (!neighbourDir.equals(direction.opposite())) {
                Cell neighbourForEmptyCell = neighbourCell.neighbour(neighbourDir);
                if (neighbourForEmptyCell != null && neighbourForEmptyCell.hasSolidEntity()) {
                    Entity entity = neighbourForEmptyCell.getSolidEntity().get();
                    if (entity instanceof MagnetBox box) {
                        MagneticPole otherPole = box.getMagneticPoleByDirection(neighbourDir.opposite());
                        MagneticPole selfPole = getMagneticPoleByDirection(neighbourDir);
                        if (selfPole != null && otherPole != null && !otherPole.isOpposite(selfPole)) {
                            if (excludeInteractionWithHooked && thisChain.isInOneChain(box)) {
                                return MagnetInteraction.NONE;
                            }
                            return MagnetInteraction.REPULSION;
                        } else {
                            // Случай, если у магнитов полюса параллельны
                            Direction newDirection = neighbourDir.clockwise();
                            MagneticPole parallelOtherPole = box.getMagneticPoleByDirection(newDirection);
                            MagneticPole parallelSelfPole = getMagneticPoleByDirection(newDirection);
                            if (parallelOtherPole != null && parallelSelfPole != null &&
                                    !parallelSelfPole.isOpposite(parallelOtherPole)) {
                                if (excludeInteractionWithHooked && thisChain.isInOneChain(box)) {
                                    return MagnetInteraction.NONE;
                                }
                                return MagnetInteraction.REPULSION;
                            }
                        }
                    } else if (entity instanceof MagneticBox box) {
                        if (excludeInteractionWithHooked && thisChain.isInOneChain(box)) {
                            return MagnetInteraction.NONE;
                        }
                        return box.canInteract(neighbourDir.opposite()).equals(MagnetInteraction.REPULSION) ? MagnetInteraction.REPULSION : MagnetInteraction.NONE;
                    }
                }
            }
        }

        return MagnetInteraction.NONE;
    }

    @Override
    protected MagnetInteraction canInteract(Direction direction, boolean excludeInteractionWithHooked) {
        Cell neighbourCell = cell.neighbour(direction);
        HookedBoxChain thisChain = new HookedBoxChain(this);
        if (neighbourCell == null) {
            return MagnetInteraction.NONE;
        }
        if (neighbourCell.getSolidEntity().isEmpty()) {
            // проверка соседних ячеек на возможное отталкивание
            return checkRepulsion(direction, excludeInteractionWithHooked);
        }
        Entity entity = neighbourCell.getSolidEntity().get();
        if (entity instanceof MagnetBox box) {
            if (excludeInteractionWithHooked && thisChain.isInOneChain(box)) {
                return MagnetInteraction.NONE;
            }
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
                    if (box.getMagnetDirection().equals(direction) && box.getMagnetDirection().isOpposite(getMagnetDirection())) {
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
            if (excludeInteractionWithHooked && thisChain.isInOneChain(box)) {
                return MagnetInteraction.NONE;
            }
            return box.canInteract(direction.opposite());
        }
        return MagnetInteraction.NONE;
    }
}
