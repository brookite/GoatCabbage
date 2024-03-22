package brookite.games.goatcabbage.model.events;

import java.util.EventObject;

public class ActionEvent extends EventObject {
    public enum Type {
        EAT, MOVE
    }

    protected Type type;

    public ActionEvent(Object source, Type eventType) {
        super(source);
        this.type = eventType;
    }
}
