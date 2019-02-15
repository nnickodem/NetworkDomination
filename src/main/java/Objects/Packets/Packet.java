package Objects.Packets;

public class Packet {

    private String team;
    private int cost;
    private int stealth;
    private String packet_type;

    public void setTeam(final String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setStealth(final int stealth) {
        this.stealth = stealth;
    }

    public int getStealth() {
        return stealth;
    }

    public String getPacketType() {
        return packet_type;
    }

    public void setPacketType(final String packet_type) {
        this.packet_type = packet_type;
    }

}
