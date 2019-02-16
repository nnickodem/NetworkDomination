package Objects.Packets;

/**
 * Parent class that all specific
 * types of packets extends.
 */
public class Packet {

    private String team;
    private int cost;
    private int stealth;
    private String packet_type;

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
    public void setCost(final int cost) {
        this.cost = cost;
    }

    /**
     * Gets the cost of the packet.
     * @return How much a packet costs to produce.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the stealth value for the packet.
     * @param stealth Stealth value of the packet.
     */
    public void setStealth(final int stealth) {
        this.stealth = stealth;
    }

    /**
     * Gets the stealth value of the packet.
     * @return Stealth value of the packet.
     */
    public int getStealth() {
        return stealth;
    }

    /**
     * Gets what the packet type is (Botnet, Crypto, ICMP, SYN)
     * @return The type of packet it is.
     */
    public String getPacketType() {
        return packet_type;
    }

    /**
     * Sets the packet type.
     * @param packet_type The type of packet it is.
     */
    public void setPacketType(final String packet_type) {
        this.packet_type = packet_type;
    }

}
