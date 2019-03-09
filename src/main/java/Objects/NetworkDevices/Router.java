package Objects.NetworkDevices;

import java.util.Arrays;
import java.util.List;

/**
 * A specialized NetworkDevice that will slow
 * down all packets owned by a team other than
 * the team that owns the router when these
 * packets try to pass through the router.
 * Only able to send ICMP packets.
 */
public class Router extends NetworkDevice {
  
    /**
     * Constructor for a Router
     * @param speed         Packet generation speed
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Router(final Integer speed,
                    final List<String> connections,
                    final Boolean hidden, final Integer max_packet,
                    final String id, final Integer index) {
        super(speed, connections, hidden, max_packet, id, index);
        setPackets(Arrays.asList("ICMP"));
    }

	public void filterPackets() {
		//TODO: implement
	}

	//TODO: router specific attributes
	//route table? interface ips?
}

