package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoatTest {
    @Test
    public void moveToDirectionsTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(3, 3).putEntity(gt);
        Assertions.assertTrue(gt.move(Direction.east()));
        Assertions.assertEquals(gt.getCell(), pd.cell(2, 3));
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
        pd.cell(4, 3).setWall(Direction.south(), true);
        Assertions.assertFalse(gt.move(Direction.south()));
    }

    @Test
    public void goatCollideWithOuterWallTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(1, 1).putEntity(gt);
        Assertions.assertFalse(gt.move(Direction.west()));
    }

    @Test
    public void goatCollideWithBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(new Box());
        Assertions.assertFalse(gt.move(Direction.west()));
    }

    @Test
    public void goatDragBoxWithBetweenBoxes() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        Box box3 = new Box();
        pd.cell(1, 1).putEntity(box3);
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        pd.cell(1, 4).putEntity(box2);
        Assertions.assertTrue(gt.canDrag(Direction.east()));
        Assertions.assertTrue(gt.startDrag(Direction.east()));

        Assertions.assertFalse(gt.move(Direction.north()));
        Assertions.assertFalse(gt.move(Direction.south()));
        Assertions.assertFalse(gt.move(Direction.east()));
        Assertions.assertFalse(gt.move(Direction.west()));

        Assertions.assertFalse(gt.canMove(Direction.north()));
        Assertions.assertFalse(gt.canMove(Direction.south()));
        Assertions.assertFalse(gt.canMove(Direction.east()));
        Assertions.assertFalse(gt.canMove(Direction.west()));
        gt.stopDrag();

        Assertions.assertFalse(gt.canMove(Direction.north()));
        Assertions.assertTrue(gt.canMove(Direction.south()));
        Assertions.assertFalse(gt.canMove(Direction.east()));
        Assertions.assertFalse(gt.canMove(Direction.west()));
    }

    @Test
    public void goatDragBoxNearWall() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(2, 2).putEntity(gt);
        pd.cell(2, 3).putEntity(box);
        pd.cell(2, 3).setWall(Direction.east(), true);
        Assertions.assertTrue(gt.canDrag(Direction.east()));
        Assertions.assertTrue(gt.startDrag(Direction.east()));

        Assertions.assertFalse(gt.canMove(Direction.north()));
        Assertions.assertFalse(gt.canMove(Direction.south()));
        Assertions.assertFalse(gt.canMove(Direction.east()));
        Assertions.assertTrue(gt.canMove(Direction.west()));

        Assertions.assertFalse(gt.move(Direction.north()));
        Assertions.assertFalse(gt.move(Direction.south()));
        Assertions.assertFalse(gt.move(Direction.east()));
        Assertions.assertTrue(gt.move(Direction.west()));

        gt.stopDrag();
    }

    @Test
    public void dragAndMoveBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(box);
        Assertions.assertTrue(gt.canDrag(Direction.east()));
        Assertions.assertTrue(gt.startDrag(Direction.east()));

        Assertions.assertFalse(gt.move(Direction.north()));
        Assertions.assertFalse(gt.move(Direction.south()));
        Assertions.assertTrue(gt.move(Direction.east()));
        Assertions.assertFalse(gt.move(Direction.east()));
        gt.stopDrag();
    }

    @Test
    public void dragAndMoveBackBoxTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(box);
        Assertions.assertTrue(gt.canDrag(Direction.east()));
        Assertions.assertTrue(gt.startDrag(Direction.east()));

        Assertions.assertFalse(gt.move(Direction.north()));
        Assertions.assertFalse(gt.move(Direction.south()));
        Assertions.assertTrue(gt.move(Direction.west()));
        Assertions.assertFalse(gt.move(Direction.west()));
        gt.stopDrag();
    }

}
