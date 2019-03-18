package Objects;

import Objects.NetworkDevices.NetworkDevice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game level object, used to hold information about the game level
 */
public class GameLevel {

	private String[][] levelMap;
	private Map<Map.Entry<String,String>, Integer> connections;
	private Map<String, NetworkDevice> idToDeviceObject;
	private String description;
	private List<String> primaryObjectives;
	private List<String> secondaryObjectives;

	public GameLevel(final String [][] levelMap,
					 final Map<Map.Entry<String, String>, Integer> connections,
					 final Map<String, NetworkDevice> idToDeviceObject, String description,
					 List<String> primaryObjectives, List<String> secondaryObjectives) {
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

	public Map<Map.Entry<String, String>, Integer> getConnections() {
		return connections;
	}

	public Map<String, NetworkDevice> getIdToDeviceObject() {
		return idToDeviceObject;
	}
  
	public String getDescription() {
    		return description; 
  	}

	public List<String> getPrimaryObjectives() {
    		return primaryObjectives; 
  	}

	public List<String> getSecondaryObjectives() {
    		return secondaryObjectives; 
  	}
}