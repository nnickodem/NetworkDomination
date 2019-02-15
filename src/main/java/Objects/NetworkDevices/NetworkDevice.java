package Objects.NetworkDevices;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for all physical network objects
 */
public class NetworkDevice {

    private String id;
    private String team;
    private ArrayList<Integer> connections;
    private int speed;
    private Boolean hidden;
    private int max_packet;
    private List<String> packets;

    /**
     *
     * @param id
     */
    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPacket(final String packet_type) {
        packets.add(packet_type);
    }

    public List getPacket() {
        return packets;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }

    public void setMaxPacket(final int max_packet) {
        this.max_packet = max_packet;
    }

    public int getMaxPacket() {
        return max_packet;
    }

    public void setTeam(final String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    public void setConnections(final ArrayList<Integer> connections) {
        this.connections = connections;
    }

    public ArrayList<Integer> getConnections() {
        return connections;
    }
    //TODO: other universal values?

}
