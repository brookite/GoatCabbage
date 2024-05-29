package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.SimpleBox;
import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    public void setNewWallTest() {
        Cell cell = new Cell(null);
        cell.putEntity(new Wall());
        Assertions.assertTrue(cell.hasSolidEntity());
        Assertions.assertInstanceOf(Wall.class, cell.getSolidEntity().get());
    }

    @Test
    public void setExistingWallTest() {
        Cell cell = new Cell(null);
        Assertions.assertTrue(cell.putEntity(new Wall()));
        Assertions.assertFalse(cell.putEntity(new Wall()));
    }

    @Test
    public void removeWallTest() {
        Cell cell = new Cell(null);
        Wall wall = new Wall();
        Assertions.assertTrue(cell.putEntity(wall));
        cell.removeEntity(wall);
        Assertions.assertFalse(cell.hasSolidEntity());
    }

    @Test
    public void putSolidEntityTest() {
        Paddock pd = new Paddock(10, 10);
        Box box = new SimpleBox();
        Box box2 = new SimpleBox();
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
        Box box = new SimpleBox();
        Cabbage cabbage = new Cabbage();
        Cell cell = pd.cell(1, 1);
        Assertions.assertTrue(cell.putEntity(box));
        Assertions.assertTrue(cell.canPutEntity(cabbage));
        Assertions.assertTrue(cell.putEntity(cabbage));
        Assertions.assertTrue(cell.getEntities().contains(cabbage));
        Assertions.assertTrue(cell.getEntities().contains(box));
    }

    @Test
    public void neighborOnBorderTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertNull(pd.cell(1, 5).neighbour(Direction.EAST));
        Assertions.assertNull(pd.cell(1, 1).neighbour(Direction.WEST));
        Assertions.assertNull(pd.cell(5, 5).neighbour(Direction.SOUTH));
        Assertions.assertNull(pd.cell(1, 1).neighbour(Direction.NORTH));
    }

    @Test
    public void putSolidEntitiesTest() {
        Paddock pd = new Paddock(10, 10);
        Box box = new SimpleBox();
        Box box2 = new SimpleBox();
        Cell cell = pd.cell(1, 1);
        Assertions.assertTrue(cell.putEntity(box));
        Assertions.assertFalse(cell.canPutEntity(box2));
        Assertions.assertFalse(cell.putEntity(box2));
    }
}
