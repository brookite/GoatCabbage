package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.Wall;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.EatEvent;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class GoatTest {
    @Test
    public void moveToDirectionsTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(3, 3).putEntity(gt);

        Assertions.assertTrue(gt.move(Direction.EAST));
        Assertions.assertEquals(gt.getCell(), pd.cell(3, 4));

        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertEquals(gt.getCell(), pd.cell(3, 3));

        Assertions.assertTrue(gt.move(Direction.SOUTH));
        Assertions.assertEquals(gt.getCell(), pd.cell(4, 3));

        Assertions.assertTrue(gt.move(Direction.NORTH));
        Assertions.assertEquals(gt.getCell(), pd.cell(3, 3));
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
        pd.cell(5, 3).putEntity(new Wall());
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
    public void goatCollideWithBoxNearOuterWallTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        pd.cell(10, 9).putEntity(gt);
        pd.cell(10, 10).putEntity(new Box());
        Assertions.assertFalse(gt.move(Direction.EAST));
    }

    @Test
    public void goatBetweenBoxes() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        Box box3 = new Box();
        pd.cell(1, 1).putEntity(box3);
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        pd.cell(1, 4).putEntity(box2);

        Assertions.assertFalse(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertFalse(gt.move(Direction.WEST));
        Assertions.assertTrue(gt.move(Direction.SOUTH));
    }

    @Test
    public void goatDragBackBoxBetweenBoxes() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        Box box2 = new Box();
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        pd.cell(1, 4).putEntity(box2);

        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertEquals(gt.getCell(), pd.cell(1, 1));
        Assertions.assertEquals(box.getCell(), pd.cell(1, 2));
        Assertions.assertEquals(box2.getCell(), pd.cell(1, 4));
    }

    @Test
    public void goatDragBoxNearWall() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(2, 2).putEntity(gt);
        pd.cell(2, 3).putEntity(box);
        pd.cell(2, 4).putEntity(new Wall());

        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertEquals(gt.getCell(), pd.cell(2, 2));
        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertEquals(gt.getCell(), pd.cell(2, 1));
    }

    @Test
    public void dragAndMoveBoxAtHorizontalTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 1).putEntity(gt);
        pd.cell(1, 2).putEntity(box);

        Assertions.assertTrue(gt.move(Direction.EAST));
        Assertions.assertEquals(gt.getCell(), pd.cell(1, 2));
        Assertions.assertEquals(box.getCell(), pd.cell(1, 3));

    }

    @Test
    public void dragAndMoveBackBoxAtHorizontalTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(1, 2).putEntity(gt);
        pd.cell(1, 3).putEntity(box);
        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertEquals(gt.getCell(), pd.cell(1, 1));
        Assertions.assertEquals(box.getCell(), pd.cell(1, 2));
    }

    @Test
    public void dragAndMoveBoxAtVerticalTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(5, 5).putEntity(gt);
        pd.cell(4, 5).putEntity(box);

        Assertions.assertTrue(gt.move(Direction.SOUTH));
        Assertions.assertEquals(gt.getCell(), pd.cell(6, 5));
        Assertions.assertEquals(box.getCell(), pd.cell(5, 5));

    }

    @Test
    public void dragAndMoveBackBoxAtVerticalTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(5, 5).putEntity(gt);
        pd.cell(6, 5).putEntity(box);
        Assertions.assertTrue(gt.move(Direction.NORTH));
        Assertions.assertEquals(gt.getCell(), pd.cell(4, 5));
        Assertions.assertEquals(box.getCell(), pd.cell(5, 5));
    }

    @Test
    public void boxDiagonallyRelativeToGoatTest() {
        Goat gt = new Goat(30);
        Paddock pd = new Paddock(10, 10);
        Box box = new Box();
        pd.cell(5, 5).putEntity(gt);
        pd.cell(6, 4).putEntity(box);

        Assertions.assertTrue(gt.move(Direction.SOUTH));
        Assertions.assertEquals(gt.getCell(), pd.cell(6, 5));
        Assertions.assertEquals(box.getCell(), pd.cell(6, 4));
    }

    @Test
    public void goatStepsAreOverTest() {
        Goat gt = new Goat(3);
        Paddock pd = new Paddock(10, 10);
        pd.cell(5, 5).putEntity(gt);

        Assertions.assertTrue(gt.move(Direction.SOUTH));
        Assertions.assertTrue(gt.move(Direction.WEST));
        Assertions.assertTrue(gt.move(Direction.NORTH));
        Assertions.assertFalse(gt.move(Direction.EAST));
        Assertions.assertFalse(gt.hasSteps());
    }

    @Test
    public void goatEatCabbageTest() {
        Goat gt = new Goat(3);
        Paddock pd = new Paddock(10, 10);
        Cabbage cb = new Cabbage();
        pd.cell(5, 5).putEntity(gt);
        pd.cell(5, 6).putEntity(cb);

        gt.addActionListener(new ActionListener() {
            @Override
            public void onActionPerformed(ActionEvent event) {
                if (event instanceof EatEvent moveEvent) {
                    throw new EventCalledException();
                }
            }
        });
        Assertions.assertThrows(EventCalledException.class, () -> {
            Assertions.assertTrue(gt.move(Direction.EAST));
        });
    }

    @Test
    public void goatMoveEventTest() {
        Goat gt = new Goat(3);
        Paddock pd = new Paddock(10, 10);
        pd.cell(5, 5).putEntity(gt);

        gt.addActionListener(new ActionListener() {
            @Override
            public void onActionPerformed(ActionEvent event) {
                if (event instanceof MoveEvent moveEvent) {
                    throw new EventCalledException();
                }
            }
        });
        Assertions.assertThrows(EventCalledException.class, () -> {
            Assertions.assertTrue(gt.move(Direction.EAST));
        });
    }

}
