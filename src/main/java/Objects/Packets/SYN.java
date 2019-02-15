package Objects.Packets;

public class SYN extends Packet {

    public SYN(final String team) {
        setPacketType("SYN");
        setTeam(team);
        setCost(1);
        setStealth(1);
    }

}
