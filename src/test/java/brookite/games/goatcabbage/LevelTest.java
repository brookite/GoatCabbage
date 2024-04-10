package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Level;
import brookite.games.goatcabbage.model.levels.data.commands.CreateWallFigureCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntitiesCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntityCommand;
import brookite.games.goatcabbage.model.levels.data.commands.SetWallCommand;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LevelTest {
    @Test
    public void jsonSerializingTest() {
        LevelGameEnvironment level = null;
        try {
            level = LevelLoader.levelFromResource("levels/test1.level");
        } catch (IOException e) {
            Assertions.fail("Level wasn't read");
        }

        Assertions.assertNotEquals(level, null);
        Level levelJson = level.getLevelObject();

        Assertions.assertEquals(levelJson.getField().getWidth(), 128);
        Assertions.assertEquals(levelJson.getField().getHeight(), 120);
        Assertions.assertEquals(levelJson.getName(), "Example Level");
        Assertions.assertEquals(levelJson.getUiProps().getLevelBackground(), "image.png");

        Assertions.assertEquals(levelJson.getGoat().getPosition(), new CellPosition(8, 8));
        Assertions.assertEquals(levelJson.getGoat().getStepAmount(), 30);

        Command[] commands = levelJson.getCommands();
        Assertions.assertEquals(commands.length, 5);
        PlaceEntitiesCommand command1 = (PlaceEntitiesCommand) commands[0];
        PlaceEntityCommand command2 = (PlaceEntityCommand) commands[1];
        PlaceEntityCommand command3 = (PlaceEntityCommand) commands[2];
        SetWallCommand command4 = (SetWallCommand) commands[3];
        CreateWallFigureCommand command5 = (CreateWallFigureCommand) commands[4];

        Assertions.assertEquals(command1.getType(), "box");
        Assertions.assertEquals(command1.getPositions().get(0), new CellPosition(3, 2));
        Assertions.assertEquals(command1.getPositions().get(1), new CellPosition(5, 7));

        Assertions.assertEquals(command2.getType(), "cabbage");
        Assertions.assertEquals(command2.getPosition(), new CellPosition(7,2));

        Assertions.assertEquals(command3.getType(), "box");
        Assertions.assertEquals(command3.getPosition(), new CellPosition(5, 2));

        Assertions.assertEquals(command3.getPosition(), new CellPosition(5, 2));

        Assertions.assertEquals(command5.getFigure().size(), 2);
        Assertions.assertEquals(Direction.EAST, command5.getFigure().get(0).getDirection());
        Assertions.assertEquals(command5.getFigure().get(0).getStep(), 1);
        Assertions.assertEquals(command5.getFigure().get(0).getLength(), 10);
        Assertions.assertEquals(command5.getFigure().get(0).getStartPosition(), new CellPosition(20, 5));

        Assertions.assertEquals(Direction.SOUTH, command5.getFigure().get(1).getDirection());
        Assertions.assertEquals(command5.getFigure().get(1).getStep(), 1);
        Assertions.assertEquals(command5.getFigure().get(1).getLength(), 10);
        Assertions.assertEquals(command5.getFigure().get(1).getStartPosition(), new CellPosition(20,14));

    }

    @Test
    public void paddockFromLevelCreationTest() {
        LevelGameEnvironment level = null;
        try {
            level = LevelLoader.levelFromResource("levels/test1.level");
            Assertions.assertNotEquals(level, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Paddock pd = level.create();
        Assertions.assertEquals(pd.getWidth(), 128);
        Assertions.assertEquals(pd.getHeight(), 120);
        Assertions.assertEquals(pd.cell(8,8).getSolidEntity().get(), pd.getGoat());
        Assertions.assertEquals(pd.getGoat().getStepAmount(), 30);
        Assertions.assertInstanceOf(Box.class, pd.cell(3, 2).getSolidEntity().get());
        Assertions.assertInstanceOf(Box.class, pd.cell(5, 7).getSolidEntity().get());
        Assertions.assertInstanceOf(Box.class, pd.cell(5, 2).getSolidEntity().get());
        Assertions.assertTrue(pd.cell(7,2).getPassableEntities().contains(pd.getCabbage()));

        Assertions.assertTrue(pd.cell(40, 2).isWall());
        for (int x = 5; x < 15; x++) {
            Assertions.assertTrue(pd.cell(20, x).isWall());
        }

        Assertions.assertFalse(pd.cell(20, 15).isWall());

        for (int y = 20; y < 30; y++) {
            Assertions.assertTrue(pd.cell(y, 14).isWall());
        }

        Assertions.assertFalse(pd.cell(30, 14).isWall());

    }
}
