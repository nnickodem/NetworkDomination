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
					final String id, final Integer index) {
		super(speed, connections, hidden, max_packet, id, index);
	}

	@Override
	public void filterPackets() {
		//TODO: implement
		//Will also remove some packets, in addition to
		//slowing packets, as the Router does.
	}
}