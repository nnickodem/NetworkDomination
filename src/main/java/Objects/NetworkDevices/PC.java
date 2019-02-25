package Objects.NetworkDevices;

import java.util.Arrays;
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
     * @param connections   List of connected devices
     * @param hidden        Whether device is hidden by fog of war
     * @param max_packet    Max number of packets device can hold
     * @param id            Unique device ID value
     */
    public PC(final Integer speed,
                  final List<String> connections,
                  final Boolean hidden, final Integer max_packet,
                  final String id) {
        super(speed, connections, hidden, max_packet, id);
        setPackets(Arrays.asList("ICMP", "Botnet", "SYN", "Crypto"));
    }
}
