package brookite.games.goatcabbage.model.events;

import java.util.EventObject;

public abstract class ActionEvent extends EventObject {

    public ActionEvent(Object source) {
        super(source);
    }
}
