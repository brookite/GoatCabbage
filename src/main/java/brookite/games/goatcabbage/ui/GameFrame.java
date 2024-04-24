package brookite.games.goatcabbage.ui;

import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.events.GameResultEvent;
import brookite.games.goatcabbage.ui.utils.ImageLoader;

import javax.swing.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private StartGameDialog startGameDialog = new StartGameDialog(this);

    public GameFrame() {
        try {
            setIconImage(ImageLoader.loadAsImageIcon("icon.png").getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame() {
        startGameDialog.setVisible(true);
    }

    public void finishGame() {
        new GameOverDialog(this, new GameResultEvent(new Goat(),true), 0, 0).setVisible(true);
    }
}
