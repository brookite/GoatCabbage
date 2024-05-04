package brookite.games.goatcabbage.ui;


import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.events.GameResultEvent;
import brookite.games.goatcabbage.model.events.GameStateListener;
import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.ui.utils.EmptyLevel;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameFrame extends JFrame {
    private StartGameDialog _startGameDialog;
    private FieldPanel _field;

    private final Game _model;

    public GameFrame() {
        /*
        TODO:
        - add many levels
        - diagrams
         */

        _model = new Game();
        try {
            _model.setEnvironments(LevelLoader.loadAllLevels());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        _model.addGameStateListener(new GameStateListener() {
            @Override
            public void onGameFinished(GameResultEvent result) {
                if (_model.started()) {
                    finishGame(result);
                }
            }
        });
        _startGameDialog = new StartGameDialog(this, _model.getEnvironments());
        _startGameDialog.addLevelSelectedListener((ActionEvent e) -> {
            GameEnvironment env = (GameEnvironment) e.getSource();
            if (env != null) {
                _model.setCurrentEnvironment(env);
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

        _field = FieldFactory.empty();
        _field.setPaddock(new EmptyLevel().create());

        add(_field, "wrap");
        pack();
        if (_field.getActorWidget() != null) {
            _field.getActorWidget().requestFocus();
        }
    }

    public void selectNewLevel() {
        _startGameDialog.setVisible(true);
    }

    public void finishGame(GameResultEvent result) {
        _model.finish();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameOverDialog gameOver = new GameOverDialog(GameFrame.this, result, _field.getUsedSteps(), _field.getMovedBox());
                gameOver.setVisible(true);
            }
        });
        for (KeyListener kl : _field.getActorWidget().getKeyListeners()) {
            _field.getActorWidget().removeKeyListener(kl);
        }
    }

    public void startGame() {
        if (_field != null) {
            remove(_field);
        }
        _field = FieldFactory.fromLevel(_model.getCurrentEnvironment());
        _model.start();
        _field.setPaddock(_model.getPaddock());
        add(_field, "wrap");
        repaint();
        revalidate();
        if (_field.getActorWidget() != null) {
            _field.getActorWidget().requestFocus();
        }
        setSize(_field.getPreferredSize());
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

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newGameItem.addActionListener((ActionEvent e) -> selectNewLevel());

        keyHelpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GameFrame.this, "Управление:\nW - вверх,\nA - влево,\nS - вниз,\nD - вправо.\n\nПри нажатии Ctrl можно тянуть к себе коробку, если она находится рядом с Козой", "Помощь", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usedGraphics = "";
                try {
                    usedGraphics = Files.readString(Paths.get(getClass().getClassLoader().getResource("images/LICENSES.txt").toURI()));
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                String text = String.format("<html><h2>GoatCabbage</h2><h4>Игра \"Коза и капуста\"</h4>Курсовой проект по дисциплине Объектно-ориентированный анализ и программирование<br>Автор: Дмитрий Шашков<br>Дата сборки: 04.05.2024<br><br>В игре использована следующая графика:<br>\n%s</html>", usedGraphics);
                try {
                    JOptionPane.showMessageDialog(GameFrame.this, text, "Об игре", JOptionPane.PLAIN_MESSAGE, ImageLoader.loadAsScaledIcon("icon.png", 128, 128));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void selectNextLevel() {
        _model.nextEnvironment();
    }
}
