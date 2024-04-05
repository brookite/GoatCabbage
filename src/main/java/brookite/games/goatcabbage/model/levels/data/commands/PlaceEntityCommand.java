package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Entities;
import brookite.games.goatcabbage.model.utils.CellPosition;

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
        Entity entity = Entities.createEntityByType(type);
        paddock.cell(position[0], position[1]).clear();
        paddock.cell(position[0], position[1]).putEntity(entity);
    }

    public String getType() {
        return type;
    }

    public CellPosition getPosition() {
        return new CellPosition(position[0], position[1]);
    }
}
