package brookite.games.goatcabbage.model.levels;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Level;
import brookite.games.goatcabbage.model.levels.data.UIProperties;
import brookite.games.goatcabbage.model.levels.data.commands.CreateWallFigureCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntitiesCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntityCommand;
import brookite.games.goatcabbage.model.levels.data.commands.SetWallCommand;

public class LevelGameEnvironment extends GameEnvironment {
    /**
     * Level is an information object about the game environment
     * with unique placement of boxes, walls and goat and cabbage position
     *
     * Levels stored in JSON format
     *
     * Full Level JSON Format:
     * <pre>
     * {
     *     "name": "Example Level",
     *     "field": {
     *         "width": 128,
     *         "height: 128
     *     },
     *     "goat": {
     *         "position": [3, 2],  // cell position format is array of: [x, y]
     *         "stepAmount": 30
     *     },
     *     "uiProps": { // this object may be omitted
     *          "levelBackground": "image.png"
     *     }
     *     "commands": [
     *          {
     *              "command": "placeEntities",
     *              "type": "box"
     *              "positions": [
     *                  [3, 2],
     *                  [5, 7],
     *                  ...
     *              ]
     *          },
     *          {"command": "placeEntity", "type": "cabbage", "position": [7, 2]},
     *          {"command": "placeEntity", "type": "box", "position": [5, 2]},
     *          {"command": "setWall", "direction": "south", "position": [5, 2]}, // directions: [north, west, south, east]
     *          {
     *              "command": "createWallFigure",
     *              "figure": [
     *                  {"isVertical": false, "direction": "south", "length": 10, "startPosition": [5, 5], "step": 1}
     *                  ...
     *              ]
     *          },
     *     ]
     * }
     * </pre>
     */
    private Level jsonLevel;

    public LevelGameEnvironment(Level level) {
        super();
        this.jsonLevel = level;
    }

    @Override
    public Paddock create() {
        Paddock paddock = new Paddock(jsonLevel.getField().getWidth(), jsonLevel.getField().getHeight());
        createEntities(paddock);
        createEntities(paddock);
        return paddock;
    }

    @Override
    protected void createEntities(Paddock paddock) {
        Goat goat = new Goat(jsonLevel.getGoat().getStepAmount());
        paddock.cell(jsonLevel.getGoat().getPosition()[1], jsonLevel.getGoat().getPosition()[0]).putEntity(goat);

        for (Command cmd : jsonLevel.getCommands()) {
            if (cmd instanceof PlaceEntitiesCommand || cmd instanceof PlaceEntityCommand) {
                cmd.execute(paddock);
            }
        }

    }

    @Override
    protected void placeWalls(Paddock paddock) {
        for (Command cmd : jsonLevel.getCommands()) {
            if (cmd instanceof CreateWallFigureCommand || cmd instanceof SetWallCommand) {
                cmd.execute(paddock);
            }
        }
    }

    @Override
    public UIProperties getUIProperties() {
        return jsonLevel.getUiProps();
    }

    public Level getLevelObject() {return jsonLevel;}
}
