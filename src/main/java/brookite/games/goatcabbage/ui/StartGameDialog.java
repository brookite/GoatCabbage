package brookite.games.goatcabbage.ui;

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
        setResizable(true);
        setSize(300, 150);
        int padding = 8;
        setLocationRelativeTo(parent);

        levelComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        playButton = new JButton("Играть");
        cancelButton = new JButton("Отмена");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        panel.add(new JLabel("Выберите уровень:"));
        panel.add(levelComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel);
        add(panel);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                for (ActionListener playActionListener : playActionListeners)
                    playActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                for (ActionListener cancelActionListener : cancelActionListeners)
                    cancelActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
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