package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.data.Command;

import java.util.Arrays;
import java.util.Objects;

public class PlaceEntityCommand extends Command {
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntityCommand that = (PlaceEntityCommand) o;
        return Objects.equals(type, that.type) && Arrays.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }

    private int[] position;

    @Override
    public void execute(Paddock paddock) {

    }

    public String getType() {
        return type;
    }

    public int[] getPosition() {
        return position;
    }
}