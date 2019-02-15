package Objects.Packets;

public class Crypto extends Packet {

    public Crypto(final String team) {
        setPacketType("Crypto");
        setTeam(team);
        setCost(13);
        setStealth(0);
    }

}
