package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.model.utils.MagneticPole;

public class Directions {
    public static Direction createDirectionByString(String direction) {
        switch (direction) {
            case "south":
                return Direction.SOUTH;
            case "north":
                return Direction.NORTH;
            case "east":
                return Direction.EAST;
            case "west":
                return Direction.WEST;
            default:
                throw new IllegalArgumentException("Неверное направление указано в уровне");
        }
    }

    public static MagneticPole createMagneticPoleByString(String direction) {
        switch (direction) {
            case "south":
                return MagneticPole.SOUTH;
            case "north":
                return MagneticPole.NORTH;
            default:
                throw new IllegalArgumentException("Неверное значение полюса задано в уровне");
        }
    }
}
