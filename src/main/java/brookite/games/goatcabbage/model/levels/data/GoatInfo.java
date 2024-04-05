package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.utils.CellPosition;

import java.util.Arrays;
import java.util.Objects;

public class GoatInfo {
    private int[] position;
    private int stepAmount;

    public CellPosition getPosition() {
        return new CellPosition(position[0], position[1]);
    }

    public int getStepAmount() {
        return stepAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoatInfo goatInfo = (GoatInfo) o;
        return stepAmount == goatInfo.stepAmount && Arrays.equals(position, goatInfo.position);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(stepAmount);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }
}
