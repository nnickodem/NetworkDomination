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

    /**
     * Sets the team which the packet belongs to.
     * @param team The team which the packet belongs to.
     */
    public void setTeam(final String team) {
        this.team = team;
    }

    /**
     * Gets the team the packet belongs to.
     * @return The team the packet belongs to.
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets how much a packet costs to produce.
     * @param cost How much a packet costs to produce.
     */
    public void setCost(final Integer cost) {
        this.cost = cost;
    }

    /**
     * Gets the cost of the packet.
     * @return How much a packet costs to produce.
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * Sets the stealth value for the packet.
     * @param stealth Stealth value of the packet.
     */
    public void setStealth(final Integer stealth) {
        this.stealth = stealth;
    }

    /**
     * Gets the stealth value of the packet.
     * @return Stealth value of the packet.
     */
    public Integer getStealth() {
        return stealth;
    }

}
