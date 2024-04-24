package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.events.GameResultEvent;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOverDialog extends JDialog {
    private final GameFrame gameFrame;
    private JLabel titleLabel;
    private JLabel stepsLabel;
    private JLabel boxesMovedLabel;
    private JButton nextLevelButton;
    private JButton finishButton;


    public GameOverDialog(GameFrame parent, GameResultEvent resultEvent, int stepsTaken, int boxesMoved) {
        super(parent, resultEvent.isWin() ? "Победа" : "Игра окончена", true);
        gameFrame = parent;
        setSize(500, 350);
        setLocationRelativeTo(parent);

        titleLabel = new JLabel(resultEvent.isWin() ? "Вы победили!" : "Игра окончена");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        stepsLabel = new JLabel("Шагов: " + stepsTaken);
        boxesMovedLabel = new JLabel("Передвинутых коробок: " + boxesMoved);

        ImageIcon winnerIcon = null;
        try {
            winnerIcon = resultEvent.isWin() ? ImageLoader.loadAsIcon("goat.png", 0.25f) : ImageLoader.loadAsIcon("cabbage.png", 0.25f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (winnerIcon != null) {
            iconLabel.setIcon(winnerIcon);
        }

        nextLevelButton = new JButton("Перейти к следующему уровню");
        finishButton = new JButton("Закончить игру");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel mainView = new JPanel();
        mainView.setLayout(new BoxLayout(mainView, BoxLayout.PAGE_AXIS));
        mainView.add(iconLabel, BorderLayout.CENTER);
        mainView.add(stepsLabel, BorderLayout.CENTER);
        mainView.add(boxesMovedLabel, BorderLayout.CENTER);

        panel.add(mainView, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(nextLevelButton);
        buttonPanel.add(finishButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        getRootPane().setDefaultButton(nextLevelButton);
    }
}