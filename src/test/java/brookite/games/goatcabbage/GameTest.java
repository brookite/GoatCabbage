package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.*;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GameTest {
    @Test
    public void cabbageEatenEventTest() {
        Game game = new Game();
        try {
            game.setEnvironments(new LevelGameEnvironment[] {
                    LevelLoader.levelFromResource("levels/test2.level")
            });
            game.start();
            Assertions.assertTrue(game.getPaddock().getGoat().hasActionListeners());
            game.getPaddock().getGoat().addActionListener(new ActionListener() {
                @Override
                public void onActionPerformed(ActionEvent event) {
                    if (event instanceof EatEvent eatEvent) {
                        Assertions.assertEquals(game.getPaddock().getCabbage(), eatEvent.getVictim());
                        Assumptions.assumeTrue(true);
                    }
                }
            });
            Assertions.assertTrue(game.started());
            game.getPaddock().getGoat().move(Direction.SOUTH);
            game.getPaddock().getGoat().move(Direction.EAST);
            Assertions.fail("Event wasn't called");
        } catch (IOException e) {
            Assertions.fail("Level wasn't loaded");
        }

    }

    @Test
    public void goatStepsAreOverTest() {
        Game game = new Game();
        try {
            game.setEnvironments(new LevelGameEnvironment[] {
                    LevelLoader.levelFromResource("levels/test2.level")
            });
            game.addGameStateListener(result -> {
                Assertions.assertFalse(result.isWin());
                Assumptions.assumeTrue(true);
            });
            Assertions.assertTrue(game.hasGameStateListeners());
            game.start();
            Assertions.assertTrue(game.started());
            game.getPaddock().getGoat().move(Direction.NORTH);
            game.getPaddock().getGoat().move(Direction.EAST);
            game.getPaddock().getGoat().move(Direction.EAST);
            Assertions.fail("Event wasn't called");
        } catch (IOException e) {
            Assertions.fail("Level wasn't loaded");
        }
    }

    @Test
    public void environmentSwitchingTest() {
        Game game = new Game();
        try {
            LevelGameEnvironment[] levels = new LevelGameEnvironment[] {
                    LevelLoader.levelFromResource("levels/test1.level"),
                    LevelLoader.levelFromResource("levels/test2.level")
            };
            game.setEnvironments(levels);
            Assertions.assertEquals(game.getCurrentEnvironment(), levels[0]);
            game.nextEnvironment();
            Assertions.assertEquals(game.getCurrentEnvironment(), levels[1]);
            game.nextEnvironment();
            Assertions.assertEquals(game.getCurrentEnvironment(), levels[0]);
        } catch (IOException e) {
            Assertions.fail("Level wasn't loaded");
        }
    }
}
