package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.ActionEvent;
import brookite.games.goatcabbage.model.events.ActionListener;
import brookite.games.goatcabbage.model.events.EatEvent;
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
    private static ImageIcon icon;
    private final JLabel stepCounter;

    private class GoatKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            Direction direction = switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> Direction.NORTH;
                case KeyEvent.VK_S -> Direction.SOUTH;
                case KeyEvent.VK_A -> Direction.WEST;
                case KeyEvent.VK_D -> Direction.EAST;
                default -> null;
            };
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
                FieldPanel field = getCell().getParent();
                if (event instanceof MoveEvent moveEvent) {
                    CellWidget oldCell = getCell();
                    oldCell.removeItem(GoatWidget.this);
                    CellWidget newCell = field.cellAt(moveEvent.getNewPosition().position());
                    newCell.addItem(GoatWidget.this);
                    stepCounter.setText(Integer.toString(getGoat().getStepAmount()));
                    oldCell.repaint();
                    newCell.repaint();
                    field.usedSteps += 1;
                    requestFocus();
                } else if (event instanceof EatEvent eatEvent) {
                    CellWidget cabbageCell = field.cellAt(eatEvent.getEating().getCell().position());
                    for (Component cmp : cabbageCell.getComponents()) {
                        if (cmp instanceof CabbageWidget cabbageWidget) {
                            cabbageCell.removeItem(cabbageWidget);
                        }
                    }
                    cabbageCell.repaint();
                }
            }
        });
        addKeyListener(new GoatKeyListener());
        setLayout(new MigLayout("nogrid"));
        stepCounter = new JLabel(Integer.toString(goat.getStepAmount()));
        stepCounter.setFont(new Font("Arial", Font.BOLD, 13));
        stepCounter.setForeground(FieldPanel.DEFAULT_COLOR);
        add(stepCounter, "pos 0% 75%");
    }

    Goat getGoat() {
        return (Goat) entity;
    }

    @Override
    public ImageIcon getSourceIcon() {
        if (icon == null) {
            try {
                icon = ImageLoader.loadAsImageIcon("goat.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return icon;
    }
}
