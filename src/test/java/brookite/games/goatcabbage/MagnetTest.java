package brookite.games.goatcabbage;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.MagnetBox;
import brookite.games.goatcabbage.model.entities.MetalBox;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagnetInteraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MagnetTest {
    @Test
    public void rotationTest() {
        MagnetBox magnet = new MagnetBox(Direction.WEST, true);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.NORTH);

        magnet = new MagnetBox(Direction.WEST, false);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.SOUTH);

        magnet = new MagnetBox(Direction.NORTH, false);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.WEST);

        magnet = new MagnetBox(Direction.SOUTH, true);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.EAST);

        magnet = new MagnetBox(Direction.EAST, true);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.NORTH);

        magnet = new MagnetBox(Direction.NORTH, true);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.EAST);

        magnet = new MagnetBox(Direction.SOUTH, false);
        Assertions.assertEquals(magnet.getMagnetDirection(), Direction.WEST);
    }

    @Test
    public void twoMagnetsAttract() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.WEST, true);
        Direction dir = Direction.EAST;
        MagnetBox secondMagnet = new MagnetBox(Direction.WEST, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(4, 5).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.ATTRACTION);
    }

    @Test
    public void twoMagnetsAttractCodirectered() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.WEST, true);
        Direction dir = Direction.NORTH;
        MagnetBox secondMagnet = new MagnetBox(Direction.EAST, false);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(3, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.ATTRACTION);
    }

    @Test
    public void oneOfMagnetsAttract() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.NORTH, true);
        Direction dir = Direction.NORTH;
        MagnetBox secondMagnet = new MagnetBox(Direction.EAST, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(3, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.ATTRACTION);
    }

    @Test
    public void verticalColumnOfNorthDirectedMagnetsWithSamePoles() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.EAST, true);
        Direction dir = Direction.SOUTH;
        MagnetBox secondMagnet = new MagnetBox(Direction.EAST, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(3, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.NONE);
    }

    @Test
    public void verticalColumnOfNorthDirectedMagnetsWithDifferentPoles() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.EAST, true);
        Direction dir = Direction.SOUTH;
        MagnetBox secondMagnet = new MagnetBox(Direction.WEST, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(3, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.NONE);
    }

    @Test
    public void twoVerticalMagnetsRepulse() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.SOUTH, true);
        Direction dir = Direction.SOUTH;
        MagnetBox secondMagnet = new MagnetBox(Direction.NORTH, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(6, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.REPULSION);
    }

    @Test
    public void twoHorizontalMagnetsRepulse() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.WEST, true);
        Direction dir = Direction.EAST;
        MagnetBox secondMagnet = new MagnetBox(Direction.EAST, true);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(4, 5).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.REPULSION);
    }

    @Test
    public void repulsionInDiagonal() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.EAST, true);
        Direction dir = Direction.WEST;
        MagnetBox secondMagnet = new MagnetBox(Direction.EAST, false);
        pd.cell(5, 5).putEntity(firstMagnet);
        pd.cell(4, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.REPULSION);
    }

    @Test
    public void noneInteraction() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.NORTH, true);
        Direction dir = Direction.EAST;
        MagnetBox secondMagnet = new MagnetBox(Direction.SOUTH, false);
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(5, 4).putEntity(secondMagnet);
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.NONE);
    }

    @Test
    public void attractMetalBox() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.WEST, true);
        Direction dir = Direction.EAST;
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(4, 5).putEntity(new MetalBox());
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.ATTRACTION);
    }

    @Test
    public void attractMetalBoxWithAllPoles() {
        Paddock pd = new Paddock(10, 10);
        MagnetBox firstMagnet = new MagnetBox(Direction.NORTH, true);
        Direction dir = Direction.EAST;
        pd.cell(4, 4).putEntity(firstMagnet);
        pd.cell(4, 5).putEntity(new MetalBox());
        Assertions.assertEquals(firstMagnet.interact(dir), MagnetInteraction.ATTRACTION);
    }
}
