package Objects.Packets;

/**
 * Specialized packet type for discovering
 * parts of the network hidden by
 * fog of war.
 */
public class ICMP extends Packet {

    public ICMP(final String team) {
        super(team);
        setCost(1);
        setStealth(1);
    }
}
