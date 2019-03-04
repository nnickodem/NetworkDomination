package Objects.Packets;

/**
 * Specialized packet type for causing
 * collisions.
 */
public class SYN extends Packet {

	public SYN(final String team) {
		super(team);
		setCost(1);
		setStealth(1);
	}
}
