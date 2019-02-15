package Objects.NetworkDevices;

import java.util.ArrayList;

public class Switch extends NetworkDevice {
    public Switch(final int speed, final String team,
              final ArrayList<Integer> connections,
              final Boolean hidden, final int max_packet,
              final String id) {
        setSpeed(speed);
        setTeam(team);
        setConnections(connections);
        setHidden(hidden);
        setMaxPacket(max_packet);
        setId(id);
    }
    //TODO: Switch specific

}
