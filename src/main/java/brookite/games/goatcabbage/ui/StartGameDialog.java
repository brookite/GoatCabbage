package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.ui.widgets.CellWidget;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

public class StartGameDialog extends JDialog {
    private final GameFrame gameFrame;
    private JComboBox<GameEnvironment> levelComboBox;
    private JButton playButton;
    private JButton cancelButton;
    private FieldPanel preview;
    private JPanel panel;

    private java.util.List<ActionListener> playActionListeners = new ArrayList<>();
    private java.util.List<ActionListener> cancelActionListeners = new ArrayList<>();

    public StartGameDialog(GameFrame parent) {
        super(parent, "Выберите уровень", true);
        gameFrame = parent;
        setResizable(false);
        setLocationRelativeTo(parent);

        levelComboBox = new JComboBox<>(parent.getGameModel().getEnvironments().toArray(new GameEnvironment[0]));
        levelComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (preview != null) {
                    panel.remove(preview);
                    panel.remove(playButton);
                    panel.remove(cancelButton);

                }
                preview = FieldFactory.fromEnvironment((GameEnvironment) e.getItem());
                preview.setCellSize(CellWidget.SMALLEST_SIZE);
                for (KeyListener ls : preview.getActorWidget().getKeyListeners()) {
                    preview.getActorWidget().removeKeyListener(ls);
                }

                panel.add(preview, "grow, wrap");
                panel.add(playButton, "split 2, sizegroup btn, tag ok");
                panel.add(cancelButton, "sizegroup btn, tag cancel");
                getRootPane().setDefaultButton(playButton);

                repaint();
                pack();
            }
        });
        preview = FieldFactory.fromEnvironment((GameEnvironment) levelComboBox.getSelectedItem());
        preview.setCellSize(CellWidget.SMALLEST_SIZE);

        playButton = new JButton("Играть");
        cancelButton = new JButton("Отмена");

        panel = new JPanel(new MigLayout("insets 8", "[grow,center]", ""));
        panel.add(new JLabel("Выберите уровень:"), "wrap");
        panel.add(levelComboBox, "alignx center, wrap");
        panel.add(new JLabel("Предварительный просмотр:"), "wrap");
        panel.add(preview, "grow, wrap");
        panel.add(playButton, "split 2, sizegroup btn, tag ok");
        panel.add(cancelButton, "sizegroup btn, tag cancel");

        add(panel);
        pack();
        setLocationRelativeTo(parent);

        playButton.addActionListener(e -> {
            dispose();
            playActionListeners.forEach(listener -> listener.actionPerformed(new ActionEvent(levelComboBox.getSelectedItem(), ActionEvent.ACTION_PERFORMED, null)));
        });

        cancelButton.addActionListener(e -> {
            dispose();
            cancelActionListeners.forEach(listener -> listener.actionPerformed(new ActionEvent(levelComboBox.getSelectedItem(), ActionEvent.ACTION_PERFORMED, null)));
        });

    }

    @Override
    public void setVisible(boolean state) {
        getRootPane().setDefaultButton(playButton);
        super.setVisible(state);
    }

    public void addLevelSelectedListener(ActionListener listener) {
        playActionListeners.add(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelActionListeners.add(listener);
    }

    public GameEnvironment getLevel() {
        return (GameEnvironment) levelComboBox.getSelectedItem();
    }
}