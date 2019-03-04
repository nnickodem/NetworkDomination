package Objects.NetworkDevices;

import java.util.Arrays;
import java.util.List;

/**
 * A specialized network device that is
 * used for generating in-level currency
 * for upgrading devices.
 * Not capable of sending any types of
 * packets.
 */
public class Server extends NetworkDevice {

	public Server(final Integer speed,
				  final List<String> connections,
				  final Boolean hidden, final Integer max_packet,
				  final String id) {
		super(speed, connections, hidden, max_packet, id);
		setPackets(Arrays.asList("ICMP"));
	}
}
