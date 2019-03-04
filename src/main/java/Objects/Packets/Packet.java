package Objects.Packets;

/**
 * Parent class that all specific
 * types of packets extends.
 */
public class Packet {

	private String team;
	private Integer cost;
	private Integer stealth;

	public Packet(final String team) {
		this.team = team;
		this.cost = 0;
		this.stealth = 0;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

	public String getTeam() {
		return team;
	}

	public void setCost(final Integer cost) {
		this.cost = cost;
	}

	public Integer getCost() {
		return cost;
	}

	public void setStealth(final Integer stealth) {
		this.stealth = stealth;
	}

	public Integer getStealth() {
		return stealth;
	}
}
