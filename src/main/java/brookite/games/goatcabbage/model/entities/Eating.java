package brookite.games.goatcabbage.model.entities;

import brookite.games.goatcabbage.model.events.EatActionListener;

public interface Eating {
    boolean eat(Entity entity);
    void addEatEventListener(EatActionListener listener);
    void removeEatEventListener(EatActionListener listener);
    boolean hasEatActionListeners();
}
