package Objects.Packets;

import javax.swing.JButton;

public class PacketInfo {

    private Long time;
    private String team;
    private String type;
    private JButton source;
    private JButton target;

    public PacketInfo(final Long time, final String team, final String type, final JButton source, final JButton target) {
        this.time = time;
        this.team = team;
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public Long getTime() {
        return time;
    }

    public String getTeam() {
        return team;
    }

    public String getType() {
        return type;
    }

    public JButton getSource() {
        return source;
    }

    public JButton getTarget() {
        return target;
    }
}
