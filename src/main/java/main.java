import GUIs.MainMenu;
import ResourceHandlers.LogHandler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Temporary
 */
public class main {
    private static final Logger logger = Logger.getLogger("errorLogger");
    public static void main(String args[]) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                logger.log(Level.SEVERE, "Uncaught exception",new Exception(e));
            }
        });
        LogHandler.setup();
        // set look and feel to the system and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception c){
            c.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}