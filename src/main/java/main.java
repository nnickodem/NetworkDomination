import GUIs.MainMenu;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Temporary
 */
public class main {
    public static void main(String args[]) {
        // set look and feel to the system and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception c){
            c.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}