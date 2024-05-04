package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoxWidget extends EntityWidget {
    private static final ImageIcon icon;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("box.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BoxWidget(Box entity) {
        super(entity);
        entity.addActionListener(new ActionListener() {
            @Override
            public void onActionPerformed(ActionEvent event) {
                if (event instanceof MoveEvent moveEvent) {
                    FieldPanel field = getCell().getParent();
                    getCell().removeItem(BoxWidget.this);
                    field.cellAt(moveEvent.getNewPosition().position()).addItem(BoxWidget.this);
                    field._movedBox += 1;
                }
            }
        });
    }

    @Override
    public ImageIcon getSourceIcon() {
        return icon;
    }
}
