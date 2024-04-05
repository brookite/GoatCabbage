package brookite.games.goatcabbage.model.levels.data.commands;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Directions;
import brookite.games.goatcabbage.model.levels.data.WallFigureFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateWallFigureCommand extends Command {
    private List<WallFigureFragment> figure;

    @Override
    public void execute(Paddock paddock) {
        for (WallFigureFragment fragment : figure) {
            int rowStep = fragment.getDirection().isVertical() ? fragment.getStep() : 1;
            int colStep = fragment.getDirection().isVertical() ? 1 : fragment.getStep();

            int rowDelta = fragment.getDirection().isVertical() ? fragment.getLength() : 1;
            int colDelta = fragment.getDirection().isVertical() ? 1 : fragment.getLength();

            for (int row = fragment.getStartPosition()[0];
                 row < fragment.getStartPosition()[0] + rowDelta;
                 row += rowStep) {
                for (int col = fragment.getStartPosition()[1];
                     col < fragment.getStartPosition()[1] + colDelta;
                     col += colStep) {
                    paddock.cell(row, col).putEntity(new Wall());
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateWallFigureCommand that = (CreateWallFigureCommand) o;
        return Objects.equals(figure, that.figure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figure);
    }

    public List<WallFigureFragment> getFigure() {
        return new ArrayList<>(figure);
    }
}
