package brookite.games.goatcabbage.model.levels.json.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.json.Command;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class SetWallCommand extends Command {
    private String direction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetWallCommand that = (SetWallCommand) o;
        return Objects.equals(direction, that.direction) && Arrays.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(direction);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }

    private int[] position;

    @Override
    public void execute(Paddock paddock) {

    }

    public String getDirection() {
        return direction;
    }

    public int[] getPosition() {
        return position;
    }
}
