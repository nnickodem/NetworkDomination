package Objects.Packets;

public class Botnet extends Packet {

    public Botnet(final String team) {
        setPacketType("Botnet");
        setTeam(team);
        setCost(1);
        setStealth(0);
    }

}
