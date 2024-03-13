package brookite.games.goatcabbage.model.levels.data;

import java.util.Arrays;
import java.util.Objects;

public class WallFigureFragment {
    private boolean isVertical;
    private String direction;
    private int length;
    private int[] startPosition;
    private int step;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallFigureFragment that = (WallFigureFragment) o;
        return isVertical == that.isVertical && length == that.length && step == that.step && Objects.equals(direction, that.direction) && Arrays.equals(startPosition, that.startPosition);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isVertical, direction, length, step);
        result = 31 * result + Arrays.hashCode(startPosition);
        return result;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public String getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public int[] getStartPosition() {
        return startPosition;
    }

    public int getStep() {
        return step;
    }
}
