package Objects.NetworkDevices;

import java.util.List;

/**
 * A specialized Router type.
 * Slows packets as a normal router, but also deletes
 * some packets.
 */
public class Firewall extends Router {

    /**
     * Constructor for a firewall.
     * @param speed         Packet generation speed
     * @param team          Team device belongs to
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public Firewall(final Integer speed, final String team,
                    final List<String> connections,
                    final Boolean hidden, final Integer max_packet,
                    final String id) {
        super(speed, team, connections, hidden, max_packet, id);
    }

    /**
     * Overriden function from the Router to include
     * the ability to "drop packets"
     */
    @Override
    public void filterPackets() {
        //TODO: No idea how to implement this
        //Will also remove some packets, in addition to
        //slowing packets, as the Router does.
    }
}
