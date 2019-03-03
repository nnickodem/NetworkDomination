package Objects.NetworkDevices;

import java.util.List;

/**
 * A specialized Router type.
 * Slows packets as a normal router, but also deletes
 * some packets.
 */
public class Firewall extends Router {

    public Firewall(final Integer speed,
                    final List<String> connections,
                    final Boolean hidden, final Integer max_packet,
                    final String id) {
        super(speed, connections, hidden, max_packet, id);
    }

    /**
     * Overriden function from the Router to include
     * the ability to "drop packets"
     */
    @Override
    public void filterPackets() {
        //TODO: implement
        //Will also remove some packets, in addition to
        //slowing packets, as the Router does.
    }
}
