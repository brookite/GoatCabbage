package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagneticPole;

public class Directions {
    public static Direction createDirectionByString(String direction) {
        return switch (direction) {
            case "south" -> Direction.SOUTH;
            case "north" -> Direction.NORTH;
            case "east" -> Direction.EAST;
            case "west" -> Direction.WEST;
            default -> throw new IllegalArgumentException("Неверное направление указано в уровне");
        };
    }

    public static MagneticPole createMagneticPoleByString(String direction) {
        return switch (direction) {
            case "south" -> MagneticPole.SOUTH;
            case "north" -> MagneticPole.NORTH;
            default -> throw new IllegalArgumentException("Неверное значение полюса задано в уровне");
        };
    }
}
