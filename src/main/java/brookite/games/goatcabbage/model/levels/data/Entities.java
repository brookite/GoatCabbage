package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.entities.*;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntityCommand;

import java.util.Map;

public class Entities {
    public static Entity createEntity(String type, Map<String, Object> properties) {
        switch (type) {
            case "box":
                return new SimpleBox();
            case "cabbage":
                return new Cabbage();
            case "wall":
                return new Wall();
            case "metal_box":
                return new MetalBox();
            case "magnet_box":
                if (properties == null) {
                    throw new IllegalArgumentException("Не заданы параметры для сущности в уровне");
                }
                if (!properties.containsKey("northPole")) {
                    throw new IllegalArgumentException("Отсутствует параметр northPole у сущности");
                }
                return new MagnetBox(
                        Directions.createDirectionByString(
                                (String) properties.get("northPole")
                        ), (boolean) properties.getOrDefault("rotateTopRight", true)
                );
            default:
                throw new IllegalArgumentException("Неверная сущность задана в уровне");
        }
    }
}
