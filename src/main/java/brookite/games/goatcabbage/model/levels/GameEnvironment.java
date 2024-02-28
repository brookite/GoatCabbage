package brookite.games.goatcabbage.model.levels;

import brookite.games.goatcabbage.model.Paddock;

public abstract class GameEnvironment {
	private Paddock paddock;
	private int goatStepAmount;

	public abstract void create();

	protected abstract void createCabbage();

	protected abstract void createGoat();

	protected abstract void createBoxes();

	protected abstract void placeWalls();

}
