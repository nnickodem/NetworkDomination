package Objects.Packets;

/**
 * Specialized Packet type for turning
 * NetworkDevices into crypto-currency
 * generators.
 */
public class Cryptojack extends Packet {

	public Cryptojack(final String team) {
		super(team);
		setCost(13);
		setStealth(0);
	}
}
