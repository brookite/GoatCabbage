package brookite.games.goatcabbage.model.entities.hookable;

import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.*;

/**
 * Класс для работы с "фигурой" из зацепленных коробок
 */
public class HookedBoxChain {
    private HashSet<HookableBox> boxes;
    private final HookableBox item;

    public HookedBoxChain(HookableBox itemOfFigure) {
        item = itemOfFigure;
        update(false);
    }

    public void update(boolean checkNeighbours) {
        // checkNeighbours проверяет, являются ли связанные коробки действительно соседями по клеткам
        boxes = new HashSet<>();
        boxes.add(item);
        _walkFigure(item, boxes, checkNeighbours);
    }

    /**
     * Найдет самую крайнюю коробку из зацепленных фигур
     * @param direction - сторона фигуры
     * @return крайняя коробка зацепленных фигур
     */
    public HookableBox getEdge(Direction direction) {
        update(true);
        Optional<HookableBox> found = boxes.stream().min((o1, o2) -> {
            CellPosition pos1 = o1.getCell().position();
            CellPosition pos2 = o2.getCell().position();
            int cmp = 0;
            if (direction == Direction.NORTH) {
                cmp = Integer.compare(pos1.row, pos2.row);
                if (cmp == 0) {
                    cmp = Integer.compare(pos1.col, pos2.col); // north-west
                }
            } else if (direction == Direction.SOUTH) {
                cmp = Integer.compare(pos1.row, pos2.row);
                if (cmp == 0) {
                    cmp = Integer.compare(pos1.col, pos2.col); // south-east
                }
                if (cmp == 1) {
                    cmp = -1;
                } else if (cmp == -1) {
                    cmp = 1;
                }
            } else if (direction == Direction.WEST) {
                cmp = Integer.compare(pos1.col, pos2.col);
                if (cmp == 0) {
                    cmp = Integer.compare(pos1.row, pos2.row); // north-west
                }
            } else if (direction == Direction.EAST) {
                cmp = Integer.compare(pos1.col, pos2.col);
                if (cmp == 0) {
                    cmp = Integer.compare(pos1.row, pos2.row); //north-east
                } else if (cmp == 1) {
                    cmp = -1;
                } else if (cmp == -1) {
                    cmp = 1;
                }
            }
            return cmp;
        });
        return found.orElse(null);
    }

    private static void _walkFigure(HookableBox box, Set<HookableBox> set, boolean checkNeighbours) {
        for (Direction direction : Direction.values()) {
            HookableBox hooked = box.getHooked(direction);
            if (hooked != null && !set.contains(hooked) && (!checkNeighbours || box.getCell().isNeighbour(hooked.getCell()))) {
                set.add(hooked);
                _walkFigure(hooked, set, checkNeighbours);
            }
        }
    }

    public HookableBox[] getFigureItems() {
        update(false);
        return boxes.toArray(new HookableBox[0]);
    }

    public HookableBox[] getUnmovedFigureItems() {
        update(true);
        return boxes.toArray(new HookableBox[0]);
    }

    public boolean isInOneChain(HookableBox other) {
        update(false);
        return boxes.contains(other);
    }
}
