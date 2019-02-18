package Objects.Packets;

/**
 * Specialized Packet type for turning
 * NetworkDevices into crypto-currency
 * generators.
 */
public class Crypto extends Packet {

    /**
     * Constructor for the Crypto packet type.
     * @param team Team that owns the packet.
     */
    public Crypto(final String team) {
        setTeam(team);
        setCost(13);
        setStealth(0);
    }

}
