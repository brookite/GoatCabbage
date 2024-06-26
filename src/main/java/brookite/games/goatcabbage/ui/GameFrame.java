package brookite.games.goatcabbage.ui;


import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.events.GameResultEvent;
import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GameFrame extends JFrame {
    private final StartGameDialog startGameDialog;
    private FieldPanel field;

    private final Game model;

    public GameFrame() {
        model = new Game();
        try {
            model.setEnvironments(LevelLoader.loadAllLevels());
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        model.addGameStateListener(result -> {
            if (model.started()) {
                finishGame(result);
            }
        });
        startGameDialog = new StartGameDialog(this);
        startGameDialog.addLevelSelectedListener((ActionEvent e) -> {
            GameEnvironment env = (GameEnvironment) e.getSource();
            if (env != null) {
                model.setCurrentEnvironment(env);
                startGame();
            }
        });
        setTitle("GoatCabbage | Коза и капуста");
        try {
            setIconImage(ImageLoader.loadAsImageIcon("icon.png").getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLayout(new MigLayout("align center center"));
        createMenu();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = FieldFactory.empty();

        add(field, "wrap");
        pack();
        if (field.getActorWidget() != null) {
            field.getActorWidget().requestFocus();
        }
    }

    public void selectNewLevel() {
        startGameDialog.setVisible(true);
    }

    public void finishGame(GameResultEvent result) {
        model.finish();
        SwingUtilities.invokeLater(() -> {
            GameOverDialog gameOver = new GameOverDialog(GameFrame.this, result, field.getUsedSteps(), field.getMovedBox());
            gameOver.setVisible(true);
        });
        for (KeyListener kl : field.getActorWidget().getKeyListeners()) {
            field.getActorWidget().removeKeyListener(kl);
        }
    }

    public Game getGameModel() {
        return model;
    }

    public void startGame() {
        if (field != null) {
            remove(field);
        }
        model.start();
        field = FieldFactory.fromGameModel(model);
        add(field, "wrap");
        repaint();
        revalidate();
        pack();
        if (field.getActorWidget() != null) {
            field.getActorWidget().requestFocus();
        }
    }

    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Игра");
        JMenuItem newGameItem = new JMenuItem("Начать новую игру");
        JMenuItem exitItem = new JMenuItem("Выход");
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Справка");
        JMenuItem keyHelpItem = new JMenuItem("Подсказка по клавишам");
        JMenuItem aboutItem = new JMenuItem("Об игре");
        helpMenu.add(keyHelpItem);
        helpMenu.add(aboutItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        exitItem.addActionListener(e -> System.exit(0));

        newGameItem.addActionListener((ActionEvent e) -> selectNewLevel());

        keyHelpItem.addActionListener(e -> JOptionPane.showMessageDialog(GameFrame.this, "Управление:\nW - вверх,\nA - влево,\nS - вниз,\nD - вправо.\n\nПри удерживании Ctrl можно тянуть к себе коробку, если она находится рядом с Козой", "Помощь", JOptionPane.INFORMATION_MESSAGE));

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder usedGraphics = new StringBuilder();
                try (InputStream inputStream = getClass().getResourceAsStream("/images/LICENSES.txt");
                     InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(streamReader)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        usedGraphics.append(line).append("\n");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(GameFrame.this, "Не удалось отобразить окно \"Об игре\"", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                String text = String.format("<html><h2>GoatCabbage</h2><h4>Игра \"Коза и капуста\", версия 2</h4>Курсовой проект по дисциплине Объектно-ориентированный анализ и программирование<br>Автор: Дмитрий Шашков<br>Дата сборки: 31.05.2024<br><br>В игре использована следующая графика:<br>\n%s</html>", usedGraphics);
                try {
                    JOptionPane.showMessageDialog(GameFrame.this, text, "Об игре", JOptionPane.PLAIN_MESSAGE, ImageLoader.loadAsScaledIcon("icon.png", 128, 128));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void selectNextLevel() {
        model.nextEnvironment();
    }
}
