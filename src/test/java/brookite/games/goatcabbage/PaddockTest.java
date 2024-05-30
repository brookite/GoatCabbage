package brookite.games.goatcabbage;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.SimpleBox;
import brookite.games.goatcabbage.model.utils.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaddockTest {
    @Test
    public void neighborTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertEquals(pd.getCells().get(6), pd.cell(3, 2).neighbour(Direction.NORTH));
        Assertions.assertEquals(pd.getCells().get(16), pd.cell(3, 2).neighbour(Direction.SOUTH));
        Assertions.assertEquals(pd.getCells().get(10), pd.cell(3, 2).neighbour(Direction.WEST));
        Assertions.assertEquals(pd.getCells().get(12), pd.cell(3, 2).neighbour(Direction.EAST));
    }


    @Test
    public void cellTest() {
        Paddock pd = new Paddock(5,5);
        Assertions.assertEquals(pd.cell(1, 1), pd.getCells().get(0));
        Assertions.assertEquals(pd.cell(1, 5), pd.getCells().get(4));
        Assertions.assertEquals(pd.cell(3, 3), pd.getCells().get(12));
        Assertions.assertEquals(pd.cell(5, 1), pd.getCells().get(20));
        Assertions.assertEquals(pd.cell(5, 5), pd.getCells().get(24));
    }

    @Test
    public void paddockFillTest() {
        Paddock pd = new Paddock(5,5);
        Box box = new SimpleBox();
        pd.cell(3, 3).putEntity(box);
        Assertions.assertTrue(pd.getCells().get(12).getSolidEntity().isPresent());
        Assertions.assertEquals(pd.getCells().get(12).getSolidEntity().get(), box);
    }

    @Test
    public void clearTest() {
        Paddock pd = new Paddock(5,5);
        Box box = new SimpleBox();
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
