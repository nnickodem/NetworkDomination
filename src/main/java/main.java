import GUIs.MainGui;
import ResourceHandlers.LogHandler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

	private static final Logger logger = Logger.getLogger("errorLogger");

	public static void main(String args[]) {
		try {
			Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.log(Level.SEVERE, "Uncaught exception", new Exception(e)));
			LogHandler.setup();
			// set look and feel to the system and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			SwingUtilities.invokeLater(MainGui::new);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Fatal Error", e);
		}
	}
}