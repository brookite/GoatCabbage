package brookite.games.goatcabbage.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartGameDialog extends JDialog {
    private JComboBox<String> levelComboBox;
    private JButton playButton;
    private JButton cancelButton;

    private java.util.List<ActionListener> playActionListeners = new ArrayList<>();
    private java.util.List<ActionListener> cancelActionListeners = new ArrayList<>();

    public StartGameDialog(Frame parent) {
        super(parent, "Выберите уровень", true);
        setResizable(false);
        int padding = 8;
        setLocationRelativeTo(parent);

        levelComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        playButton = new JButton("Играть");
        cancelButton = new JButton("Отмена");

        JPanel panel = new JPanel(new MigLayout("insets 8", "[grow,center]", ""));
        panel.add(new JLabel("Выберите уровень:"), "alignx center, wrap");
        panel.add(levelComboBox, "alignx center, wrap");

        panel.add(playButton, "split 2, sizegroup btn, tag ok");
        panel.add(cancelButton, "sizegroup btn, tag cancel");
        add(panel);
        pack();

        playButton.addActionListener(e -> {
            dispose();
            playActionListeners.forEach(listener -> listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
        });

        cancelButton.addActionListener(e -> {
            dispose();
            cancelActionListeners.forEach(listener -> listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
        });

        getRootPane().setDefaultButton(playButton);

    }

    public void addLevelSelectedListener(ActionListener listener) {
        playActionListeners.add(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelActionListeners.add(listener);
    }

    public String getLevel() {
        return (String) levelComboBox.getSelectedItem();
    }
}