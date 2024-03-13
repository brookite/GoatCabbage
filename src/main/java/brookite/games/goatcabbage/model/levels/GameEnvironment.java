package brookite.games.goatcabbage.model.levels;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.levels.data.UIProperties;

public abstract class GameEnvironment {
	private String name;

	public abstract Paddock create();

	protected abstract void createEntities(Paddock paddock);

	protected abstract void placeWalls(Paddock paddock);

	public abstract UIProperties getUIProperties();

	public String getName() {
		return name;
	}

}
