package Objects.NetworkDevices;

import dijkstra.DijkstraAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for all physical network devices.
 */
public class NetworkDevice {

	private String id;
	private String team;
	private List<String> connections;
	private Integer speed;
	private Boolean hidden;
	private Integer max_packet;
	private List<String> packets;
	private String type;
	private String target;
	private Integer index;
	private Integer mapVersion = 0;
	private DijkstraAlgorithm dijkstra;
	private boolean sending;

	public NetworkDevice(final Integer speed,
						 final List<String> connections,
						 final Boolean hidden, final Integer max_packet,
						 final String id, final Integer index) {
		this.speed = speed;
		this.team = id.substring(id.indexOf(".") + 1, id.lastIndexOf("."));
		this.connections = connections;
		this.hidden = hidden;
		this.max_packet = max_packet;
		this.id = id;
		this.packets = new ArrayList<>();
		this.type = getClass().toString().substring(getClass().toString().lastIndexOf(".") + 1);
		this.index = index;
		sending = false;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public List<String> getPackets() {
		return packets;
	}

	public void setPackets(final List<String> packets) {
		this.packets = packets;
	}

	public Boolean isHidden() {
		return hidden;
	}

	public void setHidden(final Boolean hidden) {
		this.hidden = hidden;
	}

	public Integer getMaxPacket() {
		return max_packet;
	}

	public void setMaxPacket(final Integer max_packet) {
		this.max_packet = max_packet;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(final Integer speed) {
		this.speed = speed;
		//TODO: Check that speed value is valid.
	}

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(final List<String> connections) {
		this.connections = connections;
	}

	public String getType() {
		return type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public Integer getIndex() {
		return index;
	}

	public Integer getMapVersion() {
		return mapVersion;
	}

	public void setMapVersion(final Integer pathVersion) {
		this.mapVersion = pathVersion;
	}

	public DijkstraAlgorithm getDijkstra() {
		return dijkstra;
	}

	public void setDijkstra(final DijkstraAlgorithm dijkstra) {
		this.dijkstra = dijkstra;
	}

	public boolean isSending() {
		return sending;
	}

	public void setSending(final boolean sending) {
		this.sending = sending;
	}
}