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

        Assertions.assertArrayEquals(levelJson.getGoat().getPosition(), new int[] {8, 8});
        Assertions.assertEquals(levelJson.getGoat().getStepAmount(), 30);

        Command[] commands = levelJson.getCommands();
        PlaceEntitiesCommand command1 = (PlaceEntitiesCommand) commands[0];
        PlaceEntityCommand command2 = (PlaceEntityCommand) commands[1];
        PlaceEntityCommand command3 = (PlaceEntityCommand) commands[2];
        SetWallCommand command4 = (SetWallCommand) commands[3];
        CreateWallFigureCommand command5 = (CreateWallFigureCommand) commands[4];

        Assertions.assertEquals(command1.getType(), "box");
        Assertions.assertArrayEquals(command1.getPositions().get(0), new int[] {3, 2});
        Assertions.assertArrayEquals(command1.getPositions().get(1), new int[] {5, 7});

        Assertions.assertEquals(command2.getType(), "cabbage");
        Assertions.assertArrayEquals(command2.getPosition(), new int[] {7, 2});

        Assertions.assertEquals(command3.getType(), "box");
        Assertions.assertArrayEquals(command3.getPosition(), new int[] {5, 2});

        Assertions.assertEquals(command4.getDirection(), "south");
        Assertions.assertArrayEquals(command3.getPosition(), new int[] {5, 2});

        Assertions.assertEquals(command5.getFigure().size(), 2);
        Assertions.assertEquals(command5.getFigure().get(0).getDirection(), "south");
        Assertions.assertFalse(command5.getFigure().get(0).isVertical());
        Assertions.assertEquals(command5.getFigure().get(0).getStep(), 1);
        Assertions.assertEquals(command5.getFigure().get(0).getLength(), 10);
        Assertions.assertArrayEquals(command5.getFigure().get(0).getStartPosition(), new int[]{5,5});

        Assertions.assertEquals(command5.getFigure().get(1).getDirection(), "east");
        Assertions.assertTrue(command5.getFigure().get(1).isVertical());
        Assertions.assertEquals(command5.getFigure().get(1).getStep(), 1);
        Assertions.assertEquals(command5.getFigure().get(1).getLength(), 10);
        Assertions.assertArrayEquals(command5.getFigure().get(1).getStartPosition(), new int[]{5,14});

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

        Assertions.assertTrue(pd.cell(5, 2).isWall(Direction.south()));
        for (int x = 5; x < 15; x++) {
            Assertions.assertTrue(pd.cell(5, x).isWall(Direction.south()));
        }

        for (int y = 5; y < 15; y++) {
            Assertions.assertTrue(pd.cell(y, 14).isWall(Direction.east()));
        }

        Assertions.assertFalse(pd.cell(15, 5).isWall(Direction.south()));

    }
}
