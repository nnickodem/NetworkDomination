package Objects.Packets;

public class ICMP extends Packet {

    public ICMP(final String team) {
        setPacketType("ICMP");
        setTeam(team);
        setCost(1);
        setStealth(1);
    }
}
