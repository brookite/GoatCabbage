package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.utils.Direction;

public class Directions {
    public static Direction createDirectionByString(String direction) {
        switch (direction) {
            case "south":
                return Direction.south();
            case "north":
                return Direction.north();
            case "east":
                return Direction.east();
            case "west":
                return Direction.west();
            default:
                throw new IllegalArgumentException("Invalid direction in level object");
        }
    }
}
