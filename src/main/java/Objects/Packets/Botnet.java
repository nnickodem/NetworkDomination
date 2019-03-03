package Objects.Packets;

/**
 * A specialized packet used to take control
 * of other devices.
 */
public class Botnet extends Packet {

    public Botnet(final String team) {
        super(team);
        setCost(1);
        setStealth(0);
    }
}
