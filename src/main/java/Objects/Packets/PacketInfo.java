package Objects.Packets;

import javax.swing.JButton;
import java.util.List;

public class PacketInfo {

	private Long time;
	private String team;
	private String type;
	private JButton source;
	private List<JButton> path;

	public PacketInfo(final Long time, final String team, final String type, final JButton source, final List<JButton> path) {
		this.time = time;
		this.team = team;
		this.type = type;
		this.source = source;
		this.path = path;
	}

	public Long getTime() {
		return time;
	}


	public void setTime(final Long time) {
		this.time = time;
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

	public void setSource(final JButton source) {
		this.source = source;
	}

	public List<JButton> getPath() {
		return path;
	}

	public void setPath(final List<JButton> path) {
		this.path = path;
	}
}
