package brookite.games.goatcabbage.model.events;

import brookite.games.goatcabbage.model.entities.Entity;

public class EatEvent extends ActionEvent {
    protected Entity eating;
    protected Entity victim;

    public EatEvent(Entity source, Entity victim) {
        super(source);
        this.eating = source;
        this.victim = victim;
    }

    public Entity getEating() {
        return eating;
    }

    public Entity getVictim() {
        return victim;
    }
}
