package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    public void setNewWallTest() {
        Cell cell = new Cell(null);
        cell.setWall(Direction.north(), true);
        Assertions.assertTrue(cell.isWall(Direction.north()));
        cell.setWall(Direction.south(), true);
        Assertions.assertTrue(cell.isWall(Direction.south()));
        cell.setWall(Direction.east(), true);
        Assertions.assertTrue(cell.isWall(Direction.east()));
        cell.setWall(Direction.west(), true);
        Assertions.assertTrue(cell.isWall(Direction.west()));
    }

    @Test
    public void testCorrectOfSettingWall() {
        Cell cell = new Cell(null);
        cell.setWall(Direction.north(), true);
        Assertions.assertTrue(cell.isWall(Direction.north()));
        Assertions.assertFalse(cell.isWall(Direction.east()));
        Assertions.assertFalse(cell.isWall(Direction.west()));
        Assertions.assertFalse(cell.isWall(Direction.south()));
        cell.setWall(Direction.north(), false);

        cell.setWall(Direction.south(), true);
        Assertions.assertFalse(cell.isWall(Direction.north()));
        Assertions.assertFalse(cell.isWall(Direction.east()));
        Assertions.assertFalse(cell.isWall(Direction.west()));
        Assertions.assertTrue(cell.isWall(Direction.south()));
        cell.setWall(Direction.south(), false);

        cell.setWall(Direction.east(), true);
        Assertions.assertFalse(cell.isWall(Direction.north()));
        Assertions.assertTrue(cell.isWall(Direction.east()));
        Assertions.assertFalse(cell.isWall(Direction.west()));
        Assertions.assertFalse(cell.isWall(Direction.south()));
        cell.setWall(Direction.east(), false);

        cell.setWall(Direction.west(), true);
        Assertions.assertFalse(cell.isWall(Direction.north()));
        Assertions.assertFalse(cell.isWall(Direction.east()));
        Assertions.assertTrue(cell.isWall(Direction.west()));
        Assertions.assertFalse(cell.isWall(Direction.south()));
        cell.setWall(Direction.west(), false);

        Assertions.assertFalse(cell.isWall(Direction.north()));
        Assertions.assertFalse(cell.isWall(Direction.east()));
        Assertions.assertFalse(cell.isWall(Direction.west()));
        Assertions.assertFalse(cell.isWall(Direction.south()));
    }

    @Test
    public void setExistingWallTest() {
        Cell cell = new Cell(null);
        cell.setWall(Direction.north(), true);
        cell.setWall(Direction.north(), true);
        Assertions.assertTrue(cell.isWall(Direction.north()));
        Assertions.assertTrue(cell.isWall(Direction.north()));

        cell.setWall(Direction.south(), false);
        cell.setWall(Direction.south(), false);
        Assertions.assertFalse(cell.isWall(Direction.south()));
    }

    @Test
    public void removeWallTest() {
        Cell cell = new Cell(null);
        cell.setWall(Direction.north(), true);
        Assertions.assertTrue(cell.isWall(Direction.north()));

        cell.setWall(Direction.north(), false);
        Assertions.assertFalse(cell.isWall(Direction.north()));
    }

    @Test
    public void putSolidEntityTest() {
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        Cell cell = new Cell(pd);
        Assertions.assertTrue(cell.putEntity(box));
        Assertions.assertFalse(cell.canPutEntity(box2));
        Assertions.assertEquals(cell.getSolidEntity().get(), box);
    }

    @Test
    public void putPassableEntitiesTest() {
        Paddock pd = new Paddock(10, 10);
        Cabbage cabbage = new Cabbage();
        Cabbage cabbage2 = new Cabbage();
        Cell cell = pd.cell(1, 1);
        Assertions.assertTrue(cell.putEntity(cabbage));
        Assertions.assertTrue(cell.canPutEntity(cabbage2));
        Assertions.assertTrue(cell.putEntity(cabbage2));
        Assertions.assertFalse(cell.canPutEntity(cabbage2));
        Assertions.assertFalse(cell.putEntity(cabbage2));
        Assertions.assertTrue(cell.getPassableEntities().contains(cabbage));
        Assertions.assertTrue(cell.getPassableEntities().contains(cabbage2));
    }

    @Test
    public void putSolidEntityWithPassableEntityTest() {
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Cabbage cabbage = new Cabbage();
        Cell cell = pd.cell(1, 1);
        Assertions.assertTrue(cell.putEntity(box));
        Assertions.assertTrue(cell.canPutEntity(cabbage));
        Assertions.assertTrue(cell.putEntity(cabbage));
        Assertions.assertTrue(cell.getEntities().contains(cabbage));
        Assertions.assertTrue(cell.getEntities().contains(box));
    }

    @Test
    public void putSolidEntitiesTest() {
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        Cell cell = pd.cell(1, 1);
        Assertions.assertTrue(cell.putEntity(box));
        Assertions.assertFalse(cell.canPutEntity(box2));
        Assertions.assertFalse(cell.putEntity(box2));
    }
}
