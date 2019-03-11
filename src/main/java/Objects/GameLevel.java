package Objects;

import Objects.NetworkDevices.NetworkDevice;

import java.util.List;
import java.util.Map;

/**
 * Game level object, used to hold information about the game level
 */
public class GameLevel {

	private String[][] levelMap;
	private List<Map.Entry<String,String>> connections;
	private Map<String, NetworkDevice> idToDeviceObject;
	private String description;
	private String primaryObjectives;
	private String secondaryObjectives;

	public GameLevel(final String [][] levelMap,
					 final List<Map.Entry<String, String>> connections,
					 final Map<String, NetworkDevice> idToDeviceObject, String description,
					 String primaryObjectives, String secondaryObjectives) {
		this.levelMap = levelMap;
		this.connections = connections;
		this.idToDeviceObject = idToDeviceObject;
		this.description = description;
		this.primaryObjectives = primaryObjectives;
		this.secondaryObjectives = secondaryObjectives;
	}

	public String[][] getLevelMap() {
		return levelMap;
	}

	public List<Map.Entry<String, String>> getConnections() {
		return connections;
	}

	public Map<String, NetworkDevice> getIdToDeviceObject() {
		return idToDeviceObject;
	}

	public String getDescription() { return description; }

	public String getPrimaryObjectives() { return primaryObjectives; }

	public String getSecondaryObjectives() { return secondaryObjectives; }
}
