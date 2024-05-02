package brookite.games.goatcabbage.ui;


import brookite.games.goatcabbage.ui.utils.ImageLoader;
import brookite.games.goatcabbage.ui.widgets.FieldPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    private StartGameDialog startGameDialog = new StartGameDialog(this);
    private FieldPanel field;

    private static final Dimension minimumSize = new Dimension(1230, 550);

    public GameFrame() {
        setTitle("GoatCabbage | Коза и капуста");
        try {
            setIconImage(ImageLoader.loadAsImageIcon("icon.png").getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLayout(new MigLayout("align center center"));
        setMinimumSize(minimumSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        field = new FieldPanel();
        add(field, "wrap");
        pack();
    }

    public void startGame() {
        //startGameDialog.setVisible(true);
    }

    public void finishGame() {
        //new GameOverDialog(this, new GameResultEvent(new Goat(),true), 0, 0).setVisible(true);
    }
}
