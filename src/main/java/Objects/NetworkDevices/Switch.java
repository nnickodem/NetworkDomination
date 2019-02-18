package Objects.NetworkDevices;

import java.util.List;

/**
 * A specialized type of NetworkDevice that
 * limits a player's access to other devices
 * on a VLAN basis.
 * Not capable of sending any types of
 * packets.
 */
public class Switch extends NetworkDevice {
    /**
     * Constructor for a Switch.
     * @param speed         Packet generation speed
     * @param team          Team device belongs to
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Switch(final Integer speed, final String team,
              final List<String> connections,
              final Boolean hidden, final Integer max_packet,
              final String id) {
        setSpeed(speed);
        setTeam(team);
        setConnections(connections);
        setHidden(hidden);
        setMaxPacket(max_packet);
        setId(id);
    }
    //TODO: Switch specific

}
