package brookite.games.goatcabbage.model.entities.hookable;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class HookableBox extends Box {
    private Map<Direction, HookableBox> hooked = new HashMap<>();

    protected boolean isHooks(Direction direction) {
        return hooked.containsKey(direction) && hooked.get(direction) != null;
    }

    protected HookableBox getHooked(Direction direction) {
        if (hooked.containsKey(direction)) {
            return hooked.get(direction);
        }
        return null;
    }

    protected abstract boolean canHook(Direction direction);

    protected boolean hook(Direction direction) {
        if (!canHook(direction) || isHooks(direction)) {
            return false;
        }
        Cell neighbourCell = cell.neighbour(direction);
        if (neighbourCell.getSolidEntity().isPresent() &&
                neighbourCell.getSolidEntity().get() instanceof HookableBox box
        ) {
            hooked.put(direction, box);
            if (!box.isHooks(direction.opposite())) {
                box.hook(direction.opposite());
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean canMove(Direction direction) {
        HookableBox[] figure = new HookedBoxChain(this).getUnmovedFigureItems();
        boolean result = true;
        for (HookableBox item : figure) {
            result &= item._canMove(direction);
        }
        return result;
    }

    private boolean _canMove(Direction direction) {
        Cell neighbour = cell.neighbour(direction);
        HookedBoxChain chain = new HookedBoxChain(this);
        if (neighbour.getSolidEntity().isEmpty()) {
            return true;
        } else if (neighbour.getSolidEntity().isPresent() && neighbour.getSolidEntity().get() instanceof HookableBox) {
            return chain.isInOneChain((HookableBox) neighbour.getSolidEntity().get());
        }
        return false;
    }

    @Override
    protected boolean move(Direction direction) {
        if (!canMove(direction)) {
            return false;
        }
        HashSet<HookableBox> moved = new HashSet<>();
        HookableBox head = new HookedBoxChain(this).getEdge(direction);
        return head.moveBoxLayer(direction, moved);
    }

    private boolean moveBoxLayer(Direction direction, Set<HookableBox> movedBoxes) {
        boolean result = super.move(direction);
        movedBoxes.add(this);
        if (result) {
            // сначала слой переместить
            if (hooked.containsKey(direction.clockwise()) && hooked.get(direction.clockwise()) != null && !movedBoxes.contains(hooked.get(direction.clockwise()))) {
                hooked.get(direction.clockwise()).moveBoxLayer(direction, movedBoxes);
            }
            if (hooked.containsKey(direction.clockwise().opposite()) && hooked.get(direction.clockwise().opposite()) != null && !movedBoxes.contains(hooked.get(direction.clockwise().opposite()))) {
                hooked.get(direction.clockwise().opposite()).moveBoxLayer(direction, movedBoxes);
            }
            // потом все остальные
            for (HookableBox box : hooked.values()) {
                if (!movedBoxes.contains(box)) {
                    result &= box.moveBoxLayer(direction, movedBoxes);
                }
            }
        }
        return result;
    }

    protected void dropHook(Direction direction) {
        hooked.remove(direction);
        Cell neighbourCell = cell.neighbour(direction);
        if (neighbourCell.getSolidEntity().isPresent() &&
                neighbourCell.getSolidEntity().get() instanceof HookableBox box &&
                box.isHooks(direction.opposite())
        ) {
            box.dropHook(direction.opposite());
        }
    }
}
