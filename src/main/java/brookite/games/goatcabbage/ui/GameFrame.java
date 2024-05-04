package brookite.games.goatcabbage.ui;


import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.ui.utils.EmptyLevel;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameFrame extends JFrame {
    private StartGameDialog _startGameDialog;
    private FieldPanel _field;

    private final Game _model;

    private static final Dimension minimumSize = new Dimension(1230, 550);

    public GameFrame() {
        /*
        TODO:
        - start game with empty paddock, select level
        - win/lose event
        - block push box on cabbage in model
        - widget rendering improvement
        - resize optimizations and improvements
        - diagrams
         */

        _model = new Game();
        try {
            _model.setEnvironments(LevelLoader.loadAllLevels());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        _startGameDialog = new StartGameDialog(this, _model.getEnvironments());
        _startGameDialog.addLevelSelectedListener((ActionEvent e) -> {
            GameEnvironment env = (GameEnvironment) e.getSource();
            if (env != null) {
                setLevel(env);
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
        setMinimumSize(minimumSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _field = FieldFactory.fromLevel(new EmptyLevel());
        add(_field, "wrap");
        pack();
        if (_field.getActorWidget() != null) {
            _field.getActorWidget().requestFocus();
        }
    }

    public void startGame() {
        _startGameDialog.setVisible(true);
    }

    public void setLevel(GameEnvironment level) {
        if (_field != null) {
            remove(_field);
        }
        _model.setCurrentEnvironment(level);
        _field = FieldFactory.fromLevel((LevelGameEnvironment) _model.getCurrentEnvironment());
        add(_field, "wrap");
        repaint();
        if (_field.getActorWidget() != null) {
            _field.getActorWidget().requestFocus();
        }
    }

    public void finishGame() {
        //new GameOverDialog(this, new GameResultEvent(new Goat(),true), 0, 0).setVisible(true);
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

        newGameItem.addActionListener((ActionEvent e) -> startGame());

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
                String text = String.format("<html><h2>GoatCabbage</h2><h4>Игра \"Коза и капуста\"</h4>Курсовой проект по дисциплине Объектно-ориентированный анализ и программирование<br>\n<br>В игре использована следующая графика:<br>\n%s</html>", usedGraphics);
                try {
                    JOptionPane.showMessageDialog(GameFrame.this, text, "Об игре", JOptionPane.PLAIN_MESSAGE, ImageLoader.loadAsScaledIcon("icon.png", 128, 128));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
