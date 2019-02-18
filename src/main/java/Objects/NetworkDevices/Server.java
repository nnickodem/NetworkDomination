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
    /**
     * Constructor for a Server
     * @param speed         Packet generation speed
     * @param team          Team device belongs to
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Server(final Integer speed, final String team,
                  final List<String> connections,
                  final Boolean hidden, final Integer max_packet,
                  final String id) {

        List<String> packets = Arrays.asList("ICMP");
        setPackets(packets);
        setSpeed(speed);
        setTeam(team);
        setConnections(connections);
        setHidden(hidden);
        setMaxPacket(max_packet);
        setId(id);
    }
}
