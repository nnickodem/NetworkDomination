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
public class FileHandler {

	private static final Logger logger = Logger.getLogger("errorLogger");
	private static final String levelFilePath = "resources/levels/";

	/**
	 * Reads a given level file and converts the information to a GameLevel object
	 * @param levelName level name
	 * @return GameLevel object
	 */
	public static GameLevel readLevel(final String levelName) {
		List<List<String>> levelMap = new ArrayList<>();
		Map<Map.Entry<String, String>, Integer> mapConnections = new HashMap<>();
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
				mapConnections.put(new AbstractMap.SimpleEntry<>(
						line.substring(0, line.indexOf(",")).replaceAll(" ", ""),
						line.substring(line.indexOf(",") + 1, line.lastIndexOf(",")).replaceAll(" ", "")),
						Integer.valueOf(line.substring(line.lastIndexOf(",") + 1).replaceAll(" ", "")));
				mapConnections.put(new AbstractMap.SimpleEntry<>(
						line.substring(line.indexOf(",") + 1, line.lastIndexOf(",")).replaceAll(" ", ""),
						line.substring(0, line.indexOf(",")).replaceAll(" ", "")),
						Integer.valueOf(line.substring(line.lastIndexOf(",") + 1).replaceAll(" ", "")));
				file.remove(0);
				line = file.get(0);
			}
			file.remove(0);
			//Converts text file device settings (speed, max_packet, etc.) into a map of deviceId -> settings
			line = file.get(0);
			while(line != null && !line.contains("*")){
				deviceToInfo.put(line.substring(0, line.indexOf(",")).replaceAll(" ", ""),
						new AbstractMap.SimpleEntry<>(
								Integer.valueOf(line.substring(line.indexOf(",")+1, line.lastIndexOf(",")).replaceAll(" ", "")),
								Integer.valueOf(line.substring(line.lastIndexOf(",")+1).replaceAll(" ", ""))));
				file.remove(0);
				line = file.get(0);
			}
			file.remove(0);
			//Get the Description of the desired level
			List<String> description = handleLevelInfo(file);
			//Get the Main Objectives of the desired level
			List<String> mainObjectives = handleLevelInfo(file);
			//Get the Secondary Objectives of the desired level
			List<String> secondaryObjectives = handleLevelInfo(file);

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

			return new GameLevel(mapArray, mapConnections, idToDeviceObject, description, mainObjectives, secondaryObjectives);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error reading level file", e);
			return null;
		}
	}

	private static List<String> handleLevelInfo(List<String> file) {
		List<String> infoType = new ArrayList<>();
		String line = file.get(0);
		while(line != null && !line.contains("*")){
			infoType.add(line);
			file.remove(0);
			line = file.get(0);
		}
		file.remove(0);
		return infoType;
	}

	/**
	 * Finds all connections a given device has
	 * @param mapConnections map of all connections between devices
	 * @param deviceId device ID
	 * @return List of all device IDs the given device is connected to
	 */
	private static List<String> getDeviceConnections(Map<Map.Entry<String, String>, Integer> mapConnections, String deviceId) {
		List<String> deviceConnections = new ArrayList<>();
		for(Map.Entry<Map.Entry<String, String>, Integer> connection : mapConnections.entrySet()) {
			if(connection.getKey().getKey().equals(deviceId)) {
				deviceConnections.add(connection.getKey().getValue());
			} else if(connection.getKey().getValue().equals(deviceId)) {
				deviceConnections.add(connection.getKey().getKey());
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
	private static NetworkDevice createDevice(final String deviceId, final Map.Entry<Integer, Integer> deviceSettings,
											  final List<String> deviceConnections, final int index) {
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