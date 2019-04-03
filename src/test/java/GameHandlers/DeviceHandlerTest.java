package GameHandlers;

import Objects.GameLevel;
import ResourceHandlers.FileHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeviceHandlerTest {

	private GameLevel gameLevel;

	@Before
	public void setup() {
		gameLevel = FileHandler.readLevel("Test");
	}

	@Test
	public void testGetPath() {
		assertEquals(DeviceHandler.getPath("Server.Red.1", "PC.Blue.2", gameLevel).toString(),
				"[Switch.Red.1, PC.Blue.2]");
		assertEquals(DeviceHandler.getPath("PC.Blue.2", "Server.Red.1", gameLevel).toString(),
				"[PC.Blue.3, Server.Red.1]");
	}

	@Test
	public void testGetNearestEnemy() {
		assertEquals(DeviceHandler.getNearestEnemy(gameLevel.getIdToDeviceObject().get("Server.Red.1"), gameLevel), "PC.Blue.3");
		assertEquals(DeviceHandler.getNearestEnemy(gameLevel.getIdToDeviceObject().get("PC.Blue.3"), gameLevel), "Server.Red.1");
	}

}
