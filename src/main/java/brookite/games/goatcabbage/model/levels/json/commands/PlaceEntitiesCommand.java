package brookite.games.goatcabbage.model.levels.json.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.json.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaceEntitiesCommand extends Command {
    private String type;
    private List<int[]> positions;

    @Override
    public void execute(Paddock paddock) {

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

    public List<int[]> getPositions() {
        return new ArrayList<>(positions);
    }
}
