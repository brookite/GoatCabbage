package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GoatWidget extends EntityWidget {
    private class GoatKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            Direction direction = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    direction = Direction.NORTH;
                    break;
                case KeyEvent.VK_S:
                    direction = Direction.SOUTH;
                    break;
                case KeyEvent.VK_A:
                    direction = Direction.WEST;
                    break;
                case KeyEvent.VK_D:
                    direction = Direction.EAST;
                    break;
            }
            if (direction != null && !e.isControlDown()) {
                getGoat().move(direction);
            }
        }
    }

    public GoatWidget(Goat goat) {
        super(goat);
        goat.addActionListener(new ActionListener() {
            @Override
            public void onActionPerformed(ActionEvent event) {
                if (event instanceof MoveEvent moveEvent) {
                    FieldPanel field = getCell().getParent();
                    CellWidget oldCell = getCell();
                    oldCell.removeItem(GoatWidget.this);
                    CellWidget newCell = field.cellAt(moveEvent.getNewPosition().position());
                    newCell.addItem(GoatWidget.this);
                    field.repaint();
                    requestFocus();
                }
            }
        });
        addKeyListener(new GoatKeyListener());
    }

    Goat getGoat() {
        return (Goat) entity;
    }

    @Override
    public ImageIcon getSourceIcon() {
        try {
            return ImageLoader.loadAsImageIcon("goat.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
