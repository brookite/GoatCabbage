package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Box;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.MagnetInteractEvent;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class BoxWidget extends EntityWidget {
    private static ImageIcon icon;

    public BoxWidget(Box entity) {
        super(entity);
        entity.addActionListener(new ActionListener() {
            @Override
            public void onActionPerformed(ActionEvent event) {
                FieldPanel field = getCell().getParent();
                if (event instanceof MoveEvent moveEvent) {
                    getCell().repaint();
                    getCell().removeItem(BoxWidget.this);
                    field.cellAt(moveEvent.getNewPosition().position()).addItem(BoxWidget.this);
                    field.cellAt(moveEvent.getNewPosition().position()).repaint();
                    field._movedBox += 1;
                } else if (event instanceof MagnetInteractEvent interactEvent) {
                    field.cellAt(entity.getCell().neighbour(interactEvent.getDirection()).position()).repaint();
                    repaint();
                }
            }
        });
    }

    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("box.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }
}
