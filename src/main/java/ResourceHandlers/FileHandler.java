package ResourceHandlers;

import Objects.GameLevel;
import Objects.NetworkDevices.Firewall;
import Objects.NetworkDevices.NetworkDevice;
import Objects.NetworkDevices.PC;
import Objects.NetworkDevices.Router;
import Objects.NetworkDevices.Server;
import Objects.NetworkDevices.Switch;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles reading/writing of various external resources including save files, level files, and fonts
 */
public class FileHandler { //TODO: implement save file handling, any others that are needed

	private static final Logger logger = Logger.getLogger("errorLogger");
	private static final String levelFilePath = "resources/levels/";
	private static Font gameFont;

	/**
	 * Creates and registers the game's custom font
	 */
	public static void loadFont() throws Exception {
		GraphicsEnvironment ge =
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/gameFont.ttf"));
		ge.registerFont(font);
		gameFont = font;
	}

	public static Font getGameFont() {
		return gameFont;
	}

	/**
	 * Writes level file
	 */
	//TODO: Temporary, make generic for level creator and perhaps save file
	public static void writeLevel() {
		String[][] level = new String[10][10];
		for(String[] row : level) {
			Arrays.fill(row, "-");
		}
		level[5][5] = "R1";
		level[5][4] = "E0";
		level[5][3] = "PC0";
		level[5][6] = "E0";
		level[5][7] = "S1";

		try(Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("level1.txt"), "utf-8"))) {
			String line = "";
			for(String[] row : level) {
				for(String entry : row) {
					line += entry + " ";
				}
				writer.write(line);
				((BufferedWriter) writer).newLine();
				line = "";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"Error writing level file", e);
		}
	}

	/**
	 * Reads a given level file and converts the information to a GameLevel object
	 * @param levelName level name
	 * @return GameLevel object
	 */
	//TODO: throws exception to stop program from attempting to continue?
	public static GameLevel readLevel(final String levelName) {
		List<List<String>> levelMap = new ArrayList<>();
		List<Map.Entry<String, String>> mapConnections = new ArrayList<>();
		List<String> file;
		Map<String, NetworkDevice> idToDeviceObject = new HashMap<>();
		Map<String, Map.Entry<Integer, Integer>> deviceToInfo = new HashMap<>();

		try {
			file = Files.readAllLines(Paths.get(levelFilePath + "level"+ levelName +".txt"));
			String line = file.get(0);
			//Converts level map from text file to a 2-d array
			while(line != null && !line.contains("*")) {
				levelMap.add(Arrays.asList(line.split(",")));
				file.remove(0);
				line = file.get(0);
			}
			file.remove(0);
			line = file.get(0);
			//Converts text file connections to a map of deviceId -> deviceId
			while(line != null && !line.contains("*")) {
				mapConnections.add(new AbstractMap.SimpleEntry<>(
						line.substring(0, line.indexOf(",")).replaceAll(" ", ""),
						line.substring(line.indexOf(",") + 1).replaceAll(" ", "")));
				file.remove(0);
				line = file.get(0);
			}
			file.remove(0);
			//Converts text file device settings (speed, max_packet, etc.) into a map of deviceId -> settings
			for(String l : file) {
				deviceToInfo.put(l.substring(0, l.indexOf(",")).replaceAll(" ", ""),
						new AbstractMap.SimpleEntry<>(
								Integer.valueOf(l.substring(l.indexOf(",")+1, l.lastIndexOf(",")).replaceAll(" ", "")),
								Integer.valueOf(l.substring(l.lastIndexOf(",")+1).replaceAll(" ", ""))));
			}
			String[][] mapArray = new String[levelMap.get(0).size()][levelMap.size()];
			List<String> deviceConnections;
			Map.Entry<Integer, Integer> deviceSettings;
			int index = 0;
			//Converts gathered information into a map of deviceId -> NetworkDevice object
			for(int y = 0; y < levelMap.size(); y++) {
				for(int x = 0; x < levelMap.get(0).size(); x++) {
					String deviceId = levelMap.get(y).get(x);
					mapArray[x][y] = deviceId;
					deviceConnections = getDeviceConnections(mapConnections, deviceId);
					deviceSettings = deviceToInfo.get(deviceId);
					if(deviceConnections != null && !deviceId.equals("-")) {
						idToDeviceObject.put(deviceId, createDevice(deviceId, deviceSettings, deviceConnections, index++));
					}
				}
			}

			return new GameLevel(mapArray, mapConnections, idToDeviceObject);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error reading level file", e);
			return null;
		}
	}

	/**
	 * Finds all connections a given device has
	 * @param mapConnections map of all connections between devices
	 * @param deviceId device ID
	 * @return List of all device IDs the given device is connected to
	 */
	private static List<String> getDeviceConnections(List<Map.Entry<String, String>> mapConnections, String deviceId) {
		List<String> deviceConnections = new ArrayList<>();
		for(Map.Entry<String, String> connection : mapConnections) {
			if(connection.getKey().equals(deviceId)) {
				deviceConnections.add(connection.getValue());
			} else if(connection.getValue().equals(deviceId)) {
				deviceConnections.add(connection.getKey());
			}
		}
		return deviceConnections;
	}

	/**
	 * Creates and returns a new NetworkDevice object using the given parameters
	 * @param deviceId deviceId
	 * @param deviceSettings device settings (speed, max_packet, etc)
	 * @param deviceConnections connections the device has (other device ids)
	 * @return constructed NetworkDevice object
	 */
	private static NetworkDevice createDevice(final String deviceId, final Map.Entry<Integer, Integer> deviceSettings, final List<String> deviceConnections, final int index) {
		NetworkDevice device;
		switch(deviceId.substring(0, deviceId.indexOf("."))) {
			case "Switch":
				device = new Switch(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				break;
			case "Router":
				device = new Router(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				break;
			case "Firewall":
				device = new Firewall(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				break;
			case "Server":
				device = new Server(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				device.setTarget("Switch.White.1"); //TODO: remove eventually, this is just for testing
				break;
			case "PC":
				device = new PC(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				break;
			default:
				device = new NetworkDevice(deviceSettings.getKey(), deviceConnections, false, deviceSettings.getValue(), deviceId, index);
				break;
		}
		return device;
	}
}