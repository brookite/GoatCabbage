package brookite.games.goatcabbage.model.levels.json;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.entities.Entity;

public class Entities {
    public static Entity createEntityByType(String type) {
        switch (type) {
            case "box":
                return new Box();
            case "cabbage":
                return new Cabbage();
            default:
                return null;
        }
    }
}
