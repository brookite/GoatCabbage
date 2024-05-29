package brookite.games.goatcabbage;
import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.MagneticBox;
import brookite.games.goatcabbage.model.utils.Debug;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HookedChainMoveTest {
    @Test
    public void moveHookedMagneticBoxes() {
        Paddock pd = Debug.paddockFromString(
"""
  M>U@#
   M  #
""");
        Goat gt = pd.getGoat();
        for (Cell cell : pd) {
            if (cell.hasSolidEntity() && cell.getSolidEntity().get() instanceof MagneticBox mb) {
                for (Direction dir: Direction.values()) {
                    mb.interact(dir);
                }
            }
        }
        gt.movePush(Direction.WEST);
        Assertions.assertEquals(Debug.drawPaddock(pd), """
 M>U@ #
  M   #
""");
    }

    @Test
    public void obstacleInMagneticBoxFigure() {
        Paddock pd = Debug.paddockFromString(
                """
                  M>U@#
                  #M  #
                """);
        Goat gt = pd.getGoat();
        for (Cell cell : pd) {
            if (cell.hasSolidEntity() && cell.getSolidEntity().get() instanceof MagneticBox mb) {
                for (Direction dir: Direction.values()) {
                    mb.interact(dir);
                }
            }
        }
        gt.movePush(Direction.WEST);
        Assertions.assertEquals(Debug.drawPaddock(pd),
                """
                  M>U@#
                  #M  #
                """);
    }
}
