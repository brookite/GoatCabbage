package brookite.games.goatcabbage.model.entities.hookable;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.*;

/**
 * Статические методы для работы с "фигурой" из зацепленных коробок
 */
public class HookedBoxChain {
    /**
     * Найдет самую крайнюю коробку из зацепленных фигур
     * @param direction - сторона фигуры
     * @return крайняя коробка зацепленных фигур
     */
    public static HookableBox getEdge(Direction direction, HookableBox item) {
        HashSet<HookableBox> boxes = new HashSet<>();
        boxes.add(item);
        _walkFigure(item, boxes, true);
        Optional<HookableBox> found = boxes.stream().min((Comparator<HookableBox>) (o1, o2) -> {
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
                    if (cmp == 1) {
                        cmp = -1;
                    } else if (cmp == -1) {
                        cmp = 1;
                    }
                } else if (cmp == 1) {
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

    public static HookableBox[] getFigureItems(HookableBox hookableBox) {
        HashSet<HookableBox> boxes = new HashSet<>();
        boxes.add(hookableBox);
        _walkFigure(hookableBox, boxes, false);
        return boxes.toArray(new HookableBox[0]);
    }

    public static HookableBox[] getUnmovedFigureItems(HookableBox hookableBox) {
        HashSet<HookableBox> boxes = new HashSet<>();
        boxes.add(hookableBox);
        _walkFigure(hookableBox, boxes, true);
        return boxes.toArray(new HookableBox[0]);
    }

    public static boolean isInOneChain(HookableBox first, HookableBox other) {
        HashSet<HookableBox> boxes = new HashSet<>();
        boxes.add(first);
        _walkFigure(first, boxes, false);
        return boxes.contains(other);
    }
}
