package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.model.levels.json.Command;
import brookite.games.goatcabbage.model.levels.json.Level;
import brookite.games.goatcabbage.model.levels.json.commands.CreateWallFigureCommand;
import brookite.games.goatcabbage.model.levels.json.commands.PlaceEntitiesCommand;
import brookite.games.goatcabbage.model.levels.json.commands.PlaceEntityCommand;
import brookite.games.goatcabbage.model.levels.json.commands.SetWallCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LevelTest {
    @Test
    public void jsonSerializingTest() {
        try {
            LevelGameEnvironment level = LevelLoader.levelFromResource("levels/test1.level");
            if (level != null) {
                Level levelJson = level.getLevelObject();
                Assertions.assertEquals(levelJson.getField().getWidth(), 128);
                Assertions.assertEquals(levelJson.getField().getHeight(), 120);
                Assertions.assertEquals(levelJson.getName(), "Example Level");
                Assertions.assertEquals(levelJson.getUiProps().getLevelBackground(), "image.png");

                Assertions.assertArrayEquals(levelJson.getGoat().getPosition(), new int[] {3, 2});
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
            } else {
                Assertions.fail("Missing Level object");
            }
        } catch (IOException e) {
            Assertions.fail("Level wasn't read");
        }
    }
}
