package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Entities;
import brookite.games.goatcabbage.model.utils.CellPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaceEntitiesCommand extends Command {
    private String type;
    private List<int[]> positions;

    @Override
    public void execute(Paddock paddock) {
        for (int[] position : positions) {
            Entity entity = Entities.createEntityByType(type);
            paddock.cell(position[0], position[1]).clear();
            paddock.cell(position[0], position[1]).putEntity(entity);
        }
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceEntitiesCommand that = (PlaceEntitiesCommand) o;
        return Objects.equals(type, that.type) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, positions);
    }

    public List<CellPosition> getPositions() {
        ArrayList<CellPosition> result = new ArrayList<>();
        for (int[] position : positions) {
            result.add(new CellPosition(position[0], position[1]));
        }
        return result;
    }
}
