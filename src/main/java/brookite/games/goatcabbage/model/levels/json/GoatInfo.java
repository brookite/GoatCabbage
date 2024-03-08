package brookite.games.goatcabbage.model.levels.json;

import java.util.Arrays;
import java.util.Objects;

public class GoatInfo {
    private int[] position;
    private int stepAmount;

    public int[] getPosition() {
        return position;
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
