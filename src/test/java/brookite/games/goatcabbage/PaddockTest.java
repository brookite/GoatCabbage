package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaddockTest {
    @Test
    public void neighborTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertEquals(pd.getCells().get(2), pd.neighbor(pd.cell(3, 2), Direction.north()));
        Assertions.assertEquals(pd.getCells().get(12), pd.neighbor(pd.cell(3, 2), Direction.south()));
        Assertions.assertEquals(pd.getCells().get(6), pd.neighbor(pd.cell(3, 2), Direction.west()));
        Assertions.assertEquals(pd.getCells().get(8), pd.neighbor(pd.cell(3, 2), Direction.east()));
    }

    @Test
    public void neighborOnBorderTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertNull(pd.neighbor(pd.cell(1, 5), Direction.east()));
        Assertions.assertNull(pd.neighbor(pd.cell(1, 1), Direction.west()));
        Assertions.assertNull(pd.neighbor(pd.cell(5, 5), Direction.south()));
        Assertions.assertNull(pd.neighbor(pd.cell(1, 1), Direction.north()));
    }

    @Test
    public void cellTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertEquals(pd.cell(1, 1), pd.getCells().get(0));
        Assertions.assertEquals(pd.cell(1, 5), pd.getCells().get(4));
        Assertions.assertEquals(pd.cell(3, 3), pd.getCells().get(12));
        Assertions.assertEquals(pd.cell(5, 1), pd.getCells().get(21));
        Assertions.assertEquals(pd.cell(5, 5), pd.getCells().get(24));
    }

    @Test
    public void paddockFillTest() {
        Paddock pd = new Paddock(5,5);
        Box box = new Box();
        pd.cell(3, 3).putEntity(box);
        Assertions.assertTrue(pd.getCells().get(12).getSolidEntity().isPresent());
        Assertions.assertEquals(pd.getCells().get(12).getSolidEntity().get(), box);
    }

    @Test
    public void clearTest() {
        Paddock pd = new Paddock(5,5);
        Box box = new Box();
        pd.cell(3, 3).putEntity(box);
        pd.clear();
        Assertions.assertEquals(pd.cell(3, 3).getEntities().size(), 0);
    }

    @Test
    public void connectionWithCellTest() {
        Paddock pd = new Paddock(5, 5);
        Assertions.assertEquals(pd.cell(1, 3).getOwner(), pd);
    }
}
