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
     * @param team          Team device belongs to
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Router(final Integer speed, final String team,
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

    public void filterPackets() {
        //TODO: No idea how to implement this
    }
}

    //TODO: router specific attributes
    //route table? interface ips?

