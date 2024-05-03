package brookite.games.goatcabbage.ui;


import brookite.games.goatcabbage.model.Game;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelLoader;
import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private StartGameDialog _startGameDialog = new StartGameDialog(this);
    private FieldPanel _field;

    private final Game _model;

    private static final Dimension minimumSize = new Dimension(1230, 550);

    public GameFrame() {
        /*
        TODO:
        - image icon cache optimization
        - goat step counter
        - start game with empty paddock, select level
        - win/lose event
        - widget rendering improvement
        - pull/push logic
        - resize optimizations and improvements
        - diagrams
         */

        _model = new Game();
        try {
            _model.setEnvironments(LevelLoader.loadAllLevels());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setTitle("GoatCabbage | Коза и капуста");
        try {
            setIconImage(ImageLoader.loadAsImageIcon("icon.png").getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLayout(new MigLayout("align center center"));
        setMinimumSize(minimumSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _field = FieldFactory.fromLevel((LevelGameEnvironment) _model.getCurrentEnvironment());
        add(_field, "wrap");
        pack();

        _field.getActorWidget().requestFocus();
    }

    public void startGame() {
        //startGameDialog.setVisible(true);
    }

    public void finishGame() {
        //new GameOverDialog(this, new GameResultEvent(new Goat(),true), 0, 0).setVisible(true);
    }
}
