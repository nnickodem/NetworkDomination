package Objects.NetworkDevices;

import java.util.ArrayList;
import java.util.List;

public class PC extends NetworkDevice {

    public PC(final int speed, final String team,
                  final ArrayList<Integer> connections,
                  final Boolean hidden, final int max_packet,
                  final String id) {

        List<String> packets = new ArrayList();
        setPacket("ICMP");
        setPacket("Botnet");
        setPacket("Crypto");
        setPacket("SYN");
        setSpeed(speed);
        setTeam(team);
        setConnections(connections);
        setHidden(hidden);
        setMaxPacket(max_packet);
        setId(id);
    }
}
