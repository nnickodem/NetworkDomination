package Objects.Packets;

/**
 * A specialized packet used to take control
 * of other devices.
 */
public class Botnet extends Packet {

    /**
     * Constructor for the Botnet packet type.
     * @param team Team that owns the packet.
     */
    public Botnet(final String team) {
        setTeam(team);
        setCost(1);
        setStealth(0);
    }

}
