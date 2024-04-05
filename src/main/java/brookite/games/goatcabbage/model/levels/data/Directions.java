package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.utils.Direction;

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
                throw new IllegalArgumentException("Invalid direction in level object");
        }
    }
}
