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

    public String[][] getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(final String[][] levelMap) {
        this.levelMap = levelMap;
    }

    public List<Map.Entry<String, String>> getConnections() {
        return connections;
    }

    public void setConnections(final List<Map.Entry<String, String>> connections) {
        this.connections = connections;
    }

    public Map<String, NetworkDevice> getIdToDeviceObject() {
        return idToDeviceObject;
    }

    public void setIdToDeviceObject(final Map<String, NetworkDevice> idToDeviceObject) {
        this.idToDeviceObject = idToDeviceObject;
    }

}
