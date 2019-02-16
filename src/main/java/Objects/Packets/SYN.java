package Objects.Packets;

/**
 * Specialized packet type for causing
 * collisions.
 */
public class SYN extends Packet {

    /**
     * Constructor for SYN packet type.
     * @param team Team that owns the packet.
     */
    public SYN(final String team) {
        setTeam(team);
        setCost(1);
        setStealth(1);
    }

}
