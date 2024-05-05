package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.MoveEvent;
import brookite.games.goatcabbage.model.utils.Direction;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GoatWidget extends EntityWidget {
    private static final ImageIcon icon;
    private JLabel _stepCounter;

    static {
        try {
            icon = ImageLoader.loadAsImageIcon("goat.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                boolean movePushResult = getGoat().movePush(direction);
                if (!movePushResult) {
                    getGoat().move(direction);
                }
            } else if (direction != null) {
                getGoat().movePull(direction);
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
                    _stepCounter.setText(Integer.toString(getGoat().getStepAmount()));
                    oldCell.repaint();
                    newCell.repaint();
                    field._usedSteps += 1;
                    requestFocus();
                }
            }
        });
        addKeyListener(new GoatKeyListener());
        setLayout(new MigLayout("nogrid"));
        _stepCounter = new JLabel(Integer.toString(goat.getStepAmount()));
        _stepCounter.setFont(new Font("Arial", Font.BOLD, 13));
        _stepCounter.setForeground(FieldPanel.DEFAULT_COLOR);
        add(_stepCounter, "pos 0% 75%");
    }

    Goat getGoat() {
        return (Goat) entity;
    }

    @Override
    public ImageIcon getSourceIcon() {
        return icon;
    }
}
