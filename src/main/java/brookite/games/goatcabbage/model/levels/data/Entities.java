package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.entities.Wall;

public class Entities {
    public static Entity createEntityByType(String type) {
        switch (type) {
            case "box":
                return new Box();
            case "cabbage":
                return new Cabbage();
            case "wall":
                return new Wall();
            default:
                throw new IllegalArgumentException("Invalid entity in level object");
        }
    }
}
