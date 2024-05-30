package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.events.GameResultEvent;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOverDialog extends JDialog {
    private final GameFrame gameFrame;

    public GameOverDialog(GameFrame parent, GameResultEvent resultEvent, int stepsTaken, int boxesMoved) {
        super(parent, resultEvent.isWin() ? "Победа" : "Игра окончена", true);
        gameFrame = parent;
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout("wrap 1, insets 10", "[center]", "[top]20[top]push[center]push[bottom]"));

        JButton nextLevelButton = new JButton("Следующий уровень");
        JButton finishButton = new JButton("Закончить игру");

        JLabel titleLabel = new JLabel(resultEvent.isWin() ? "Вы победили!" : "Игра окончена");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, "alignx center, wrap");

        Icon winnerIcon = null;
        try {
            winnerIcon = resultEvent.isWin() ? ImageLoader.loadAsScaledIcon("goat.png", 0.25f) : ImageLoader.loadAsScaledIcon("cabbage.png", 0.25f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение победителя", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (winnerIcon != null) {
            iconLabel.setIcon(winnerIcon);
        }
        panel.add(iconLabel, "alignx center, wrap");

        JLabel stepsLabel = new JLabel("Шагов: " + stepsTaken);
        JLabel boxesMovedLabel = new JLabel("Передвинутых коробок: " + boxesMoved);
        panel.add(stepsLabel, "alignx center, wrap");
        panel.add(boxesMovedLabel, "alignx center, wrap");

        panel.add(finishButton, "split 2, tag no");
        panel.add(nextLevelButton, "tag yes");

        add(panel);
        pack();
        setLocationRelativeTo(parent);

        nextLevelButton.addActionListener(e -> {
            gameFrame.selectNextLevel();
            gameFrame.startGame();
            dispose();
        });

        finishButton.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(nextLevelButton);
    }
}