package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.ui.widgets.CellWidget;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;

public class StartGameDialog extends JDialog {
    private JComboBox<GameEnvironment> levelComboBox;
    private JButton playButton;
    private JButton cancelButton;
    private FieldPanel preview;

    private java.util.List<ActionListener> playActionListeners = new ArrayList<>();
    private java.util.List<ActionListener> cancelActionListeners = new ArrayList<>();

    public StartGameDialog(Frame parent, Collection<GameEnvironment> environments) {
        super(parent, "Выберите уровень", true);
        setResizable(false);
        setLocationRelativeTo(parent);

        preview = FieldFactory.empty();

        levelComboBox = new JComboBox<>(environments.toArray(new GameEnvironment[0]));
        levelComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                preview.setPaddock(((GameEnvironment)e.getItem()).create());
                preview.setCellSize(CellWidget.SMALLEST_SIZE);
                repaint();
                pack();
            }
        });
        preview.setPaddock(((GameEnvironment)levelComboBox.getSelectedItem()).create());
        preview.setCellSize(CellWidget.SMALLEST_SIZE);

        playButton = new JButton("Играть");
        cancelButton = new JButton("Отмена");

        JPanel panel = new JPanel(new MigLayout("insets 8", "[grow,center]", ""));
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