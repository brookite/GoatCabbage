package brookite.games.goatcabbage;

import brookite.games.goatcabbage.ui.GameFrame;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatMacDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GameFrame frame = new GameFrame();
                    frame.setVisible(true);
                    frame.startGame();
                }
        });
    }
}