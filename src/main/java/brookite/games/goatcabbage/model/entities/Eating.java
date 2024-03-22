package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.events.EatActionListener;

public interface Eating {
    public boolean eat(Entity entity);
    public void addEatEventListener(EatActionListener listener);
    public void removeEatEventListener(EatActionListener listener);

}
