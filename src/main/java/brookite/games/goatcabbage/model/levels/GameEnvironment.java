package brookite.games.goatcabbage.model.levels;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.json.UIProperties;

import java.util.Map;

public abstract class GameEnvironment {
	private Paddock paddock;
	private int goatStepAmount;
	private String name;

	public abstract void create();

	protected abstract void createEntities();

	protected abstract void placeWalls();

	public abstract UIProperties getUIProperties();

	public String getName() {
		return name;
	}

}
