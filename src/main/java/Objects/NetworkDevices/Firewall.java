package Objects.NetworkDevices;

import java.util.ArrayList;

public class Firewall extends Router {

    public Firewall(final int speed, final String team,
                    final ArrayList<Integer> connections,
                    final Boolean hidden, final int max_packet,
                    final String id) {
        super(speed, team, connections, hidden, max_packet, id);
    }

    @Override
    public void filterPackets() {
        //TODO: No idea how to implement this
        //Will also remove some packets, in addition to
        //slowing packets, as the Router does.
    }
}
