package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoatTest {
    @Test
    public void moveToDirectionsTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(3, 3).putEntity(gt);
        Assertions.assertTrue(gt.move(Direction.EAST));
        Assertions.assertEquals(gt.getCell(), pd.cell(3, 4));
    }

    @Test
    public void connectionWithCellTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(3, 3).putEntity(gt);
        Assertions.assertEquals(gt.getCell(), pd.cell(3, 3));
    }

    @Test
    public void goatCollideWithInnerWallTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(4, 3).putEntity(gt);
        pd.cell(4, 3).putEntity(new Wall());
        Assertions.assertFalse(gt.move(Direction.SOUTH));
    }

    @Test
    public void goatCollideWithOuterWallTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(1, 1).putEntity(gt);
        Assertions.assertFalse(gt.move(Direction.WEST));
    }

    @Test
    public void goatCollideWithBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(new Box());
        Assertions.assertFalse(gt.move(Direction.WEST));
    }

    @Test
    public void goatDragBoxBetweenBoxes() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        Box box3 = new Box();
        pd.cell(1, 1).putEntity(box3);
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        pd.cell(1, 4).putEntity(box2);
        Assertions.assertTrue(gt.canTake(Direction.EAST));
        Assertions.assertTrue(gt.take(Direction.EAST));

        Assertions.assertFalse(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.SOUTH));
        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertFalse(gt.move(Direction.WEST));

        Assertions.assertFalse(gt.canMove(Direction.NORTH));
        Assertions.assertFalse(gt.canMove(Direction.SOUTH));
        Assertions.assertFalse(gt.canMove(Direction.EAST));
        Assertions.assertFalse(gt.canMove(Direction.WEST));

        Assertions.assertFalse(gt.canMove(Direction.NORTH));
        Assertions.assertTrue(gt.canMove(Direction.SOUTH));
        Assertions.assertFalse(gt.canMove(Direction.EAST));
        Assertions.assertFalse(gt.canMove(Direction.WEST));
    }

    @Test
    public void goatDragBoxNearWall() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(2, 2).putEntity(gt);
        pd.cell(2, 3).putEntity(box);
        pd.cell(2, 3).putEntity(new Wall());
        Assertions.assertTrue(gt.canTake(Direction.EAST));
        Assertions.assertTrue(gt.take(Direction.EAST));

        Assertions.assertFalse(gt.canMove(Direction.NORTH));
        Assertions.assertFalse(gt.canMove(Direction.SOUTH));
        Assertions.assertFalse(gt.canMove(Direction.EAST));
        Assertions.assertTrue(gt.canMove(Direction.WEST));

        Assertions.assertFalse(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.SOUTH));
        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertTrue(gt.move(Direction.WEST));
    }

    @Test
    public void dragAndMoveBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(box);
        Assertions.assertTrue(gt.canTake(Direction.EAST));
        Assertions.assertTrue(gt.take(Direction.EAST));

        Assertions.assertFalse(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.SOUTH));
        Assertions.assertTrue(gt.move(Direction.EAST));
        Assertions.assertFalse(gt.move(Direction.EAST));
    }

    @Test
    public void dragAndMoveBackBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        Assertions.assertTrue(gt.canTake(Direction.EAST));
        Assertions.assertTrue(gt.take(Direction.EAST));

        Assertions.assertFalse(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.SOUTH));
        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertFalse(gt.move(Direction.WEST));
    }

}
