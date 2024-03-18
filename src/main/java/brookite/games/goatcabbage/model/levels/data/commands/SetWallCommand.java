package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Directions;

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
        paddock.cell(position[1], position[0]).setWall(Directions.createDirectionByString(direction), true);
    }

    public String getDirection() {
        return direction;
    }

    public int[] getPosition() {
        return position;
    }
}
