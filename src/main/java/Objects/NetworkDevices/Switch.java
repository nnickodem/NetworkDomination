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

	public Switch(final Integer speed,
                  final List<String> connections,
                  final Boolean hidden, final Integer max_packet,
                  final String id, final Integer index) {
	    super(speed, connections, hidden, max_packet, id, index);
	}
	//TODO: Switch specific
}