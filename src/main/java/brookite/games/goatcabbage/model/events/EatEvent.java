package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.entities.Eating;
import brookite.games.goatcabbage.model.entities.Entity;

public class EatEvent extends ActionEvent {
    protected Eating eating;
    protected Entity victim;

    public EatEvent(Eating source, Entity victim) {
        super(source, Type.EAT);
        this.eating = source;
        this.victim = victim;
    }

    public Eating getEating() {
        return eating;
    }

    public Entity getVictim() {
        return victim;
    }
}
