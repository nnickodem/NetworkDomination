package Utils;

import Objects.GameLevel;

public class TestUtils {

	public static GameLevel createGameLevel() {
		String[][] levelMap = {{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
				{"-", "-", "-", "-", "-", "-", "Server.Red.1", "-", "-", "-", "-", "-"},
				{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
				{"-", "-", "-", "PC.Blue.3", "-", "-", "Switch.White.1", "-", "-", "-", "-", "-"},
				{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
				{"-", "Router.Yellow.1", "-", "-", "PC.Blue.2", "-", "-", "-", "PC.Blue.1", "-", "-", "-"},
				{"-", "-", "-", "-", "-", "-", "PC.Red.1", "-", "-", "-", "-", "-"}
		};

		return null;
	}


	public static void assertDoesNotThrow(FailingRunnable action){
		try {
			action.run();
		} catch(Exception ex) {
			throw new Error("expected action not to throw, but it did!", ex);
		}
	}

	@FunctionalInterface public interface FailingRunnable { void run() throws Exception; }
}
