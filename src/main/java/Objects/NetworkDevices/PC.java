package Objects.NetworkDevices;

import java.util.ArrayList;
import java.util.List;

/**
 * A specialized NetworkDevice that will
 * serve as the mainstay of the player's
 * botnet.  Capable of sending out all types
 * of packets.
 */
public class PC extends NetworkDevice {

    /**
     * Constructor for a PC
     * @param speed         Packet generation speed
     * @param team          Team device belongs to
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
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
