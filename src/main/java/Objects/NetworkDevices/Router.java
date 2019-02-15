package Objects.NetworkDevices;

import java.util.ArrayList;
import java.util.List;

public class Router extends NetworkDevice {

    public Router(final int speed, final String team,
                    final ArrayList<Integer> connections,
                    final Boolean hidden, final int max_packet,
                    final String id) {

        List<String> packets = new ArrayList();
        setPacket("ICMP");
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

