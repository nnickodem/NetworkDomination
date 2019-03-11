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
	private List<Map.Entry<String,String>> connections;
	private Map<String, NetworkDevice> idToDeviceObject;
	private Map<Integer, String> indexToId;
	private int[][] adjacencyMatrix;

	public GameLevel(final String [][] levelMap,
					 final List<Map.Entry<String, String>> connections,
					 final Map<String, NetworkDevice> idToDeviceObject) {
		this.levelMap = levelMap;
		this.connections = connections;
		this.idToDeviceObject = idToDeviceObject;
		generateAdjacencyMatrix();
	}

	/**
	 * Creates adjacency matrix from the device list
	 */
	private void generateAdjacencyMatrix() {
		adjacencyMatrix = new int[idToDeviceObject.size()][idToDeviceObject.size()];
		indexToId = new HashMap<>();
		for(int[] row : adjacencyMatrix) {
			Arrays.fill(row, 0);
		}
		for(Map.Entry<String, NetworkDevice> device : idToDeviceObject.entrySet()) {
			indexToId.put(device.getValue().getIndex(), device.getKey());
			for(String connection : device.getValue().getConnections()) {
				adjacencyMatrix[device.getValue().getIndex()][idToDeviceObject.get(connection).getIndex()] = 1;
			}
		}
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

	public Map<Integer, String> getIndexToId() {
		return indexToId;
	}

	public int[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
}