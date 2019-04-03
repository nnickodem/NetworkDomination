package ResourceHandlers;

import Objects.GameLevel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileHandlerTest {

	@Test
	public void testRead() {
		GameLevel gameLevel = FileHandler.readLevel("Test");
		assertNotNull(gameLevel);
		assertEquals(gameLevel.getConnections().toString(), "{PC.Blue.3=PC.Blue.2=1, PC.Blue.2=PC.Blue.3=1, " +
				"Server.Red.1=Switch.Red.1=1, Switch.Red.1=Server.Red.1=1, Server.Red.1=PC.Blue.3=1, " +
				"PC.Blue.3=Server.Red.1=1, Switch.Red.1=PC.Blue.2=1, PC.Blue.2=Switch.Red.1=1, " +
				"PC.Blue.3=Switch.Red.1=1, Switch.Red.1=PC.Blue.3=1}");
		assertEquals(gameLevel.getIdToDeviceObject().toString(), "{PC.Blue.2=NetworkDevice{id='PC.Blue.2', team='Blue'}, " +
				"PC.Blue.3=NetworkDevice{id='PC.Blue.3', team='Blue'}, Server.Red.1=NetworkDevice{id='Server.Red.1', team='Red'}, " +
				"Switch.Red.1=NetworkDevice{id='Switch.Red.1', team='Red'}}");
		assertEquals(gameLevel.getDescription().toString(), "[This is a test level, Testing]");
		assertEquals(gameLevel.getPrimaryObjectives().toString(), "[- Testing]");
		assertEquals(gameLevel.getSecondaryObjectives().toString(), "[- Test]");
	}
}
