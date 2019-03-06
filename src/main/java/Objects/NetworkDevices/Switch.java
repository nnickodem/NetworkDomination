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
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Switch(final Integer speed,
              final List<String> connections,
              final Boolean hidden, final Integer max_packet,
              final String id, final Integer index) {
        super(speed, connections, hidden, max_packet, id, index);
    }
    //TODO: Switch specific

}
