package Objects.NetworkDevices;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for all physical network devices.
 */
public class NetworkDevice {

    private String id;
    private String team;
    private List<String> connections;
    private Integer speed;
    private Boolean hidden;
    private Integer max_packet;
    private List<String> packets;
    private String type;
    private Integer index;

    public NetworkDevice(final Integer speed,
                    final List<String> connections,
                    final Boolean hidden, final Integer max_packet,
                    final String id, final Integer index) {
        this.speed = speed;
        this.team = id.substring(id.indexOf(".") + 1, id.lastIndexOf("."));
        this.connections = connections;
        this.hidden = hidden;
        this.max_packet = max_packet;
        this.id = id;
        this.packets = new ArrayList<>();
        this.type = getClass().toString().substring(getClass().toString().lastIndexOf(".") + 1);
        this.index = index;
    }

    /**
     * Returns the unique device ID
     * @return Unique device ID
     */
    public String getId() {
        return id;
    }
    /**
     * Sets the ID of the device.
     * @param id The unique ID given to the device.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the last of packet types the device can send.
     * @return The list of packet types the device can send.
     */
    public List<String> getPackets() {
        return packets;
    }

    /**
     * Sets the types of packets each device can send.
     * @param packets The types of packets each device can send.
     */
    public void setPackets(final List<String> packets) {
        this.packets = packets;
    }

    /**
     * Gets whether or not the device is hidden by fog of war.
     * @return Hidden variable.
     */
    public Boolean isHidden() {
        return hidden;
    }

    /**
     * Sets whether a device is hidden by fog of war.
     * @param hidden Hidden variable.
     */
    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Gets the number of packets the device can hold.
     * @return Number of packets the device can hold.
     */
    public Integer getMaxPacket() {
        return max_packet;
    }

    /**
     * Sets the number of packets the device can hold.
     * @param max_packet Number of packets the device can hold.
     */
    public void setMaxPacket(final Integer max_packet) {
        this.max_packet = max_packet;
    }

    /**
     * Returns the name of the team of the device.
     * @return The team name.
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets the name of the team for the device.
     * @param team The team name.
     */
    public void setTeam(final String team) {
        this.team = team;
    }

    /**
     * Gets the speed with which the device generates packets.
     * @return Packet generation speed.
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * Sets the speed with which the device generates packets.
     * @param speed Speed of packet generation (3, 4, or 5)
     */
    public void setSpeed(final Integer speed) {
        this.speed = speed;
        //TODO: Check that speed value is valid.
    }

    /**
     * Gets the list of connected devices.
     * @return List of connected devices.
     */
    public List<String> getConnections() {
        return connections;
    }

    /**
     * Sets the list of connected devices.
     * @param connections List of connected devices.
     */
    public void setConnections(final List<String> connections) {
        this.connections = connections;
    }

    public String getType() {
        return type;
    }
}
