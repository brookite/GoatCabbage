package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.model.levels.data.Command;

import java.util.Arrays;
import java.util.Objects;

public class SetWallCommand extends Command {

    private int[] position;

    @Override
    public void execute(Paddock paddock) {
        paddock.cell(position[0], position[1]).clear();
        paddock.cell(position[0], position[1]).putEntity(new Wall());
    }

    public int[] getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetWallCommand that = (SetWallCommand) o;
        return Objects.deepEquals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(position);
    }
}
