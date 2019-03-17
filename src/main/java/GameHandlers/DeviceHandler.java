package GameHandlers;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import dijkstra.DijkstraAlgorithm;
import dijkstra.Edge;
import dijkstra.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Currently holds path creation for packets
 *
 * Algorithm ripped from:
 * https://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 *
 * Note it has been heavily modified
 */
public class DeviceHandler {

	/**
	 * Get the shortest path from a source device to a target device
	 * @param sourceId ID of source device
	 * @param destinationId ID of target device
	 * @param gameLevel GameLevel object
	 * @param mapVersion version of the LevelGUIs map
	 * @return path in List<String> form
	 */
	public static List<String> getPath(final String sourceId, final String destinationId, final GameLevel gameLevel, final Integer mapVersion) {
		NetworkDevice source = gameLevel.getIdToDeviceObject().get(sourceId);
		DijkstraAlgorithm dijkstraAlgorithm;
		if(mapVersion > source.getMapVersion()) {
			dijkstraAlgorithm = createDijkstra(source.getTeam(), gameLevel);
			dijkstraAlgorithm.execute(source.getId());
			source.setDijkstra(dijkstraAlgorithm);
			source.setMapVersion(mapVersion);
		} else {
			dijkstraAlgorithm = source.getDijkstra();
		}

		return dijkstraAlgorithm.getPath(destinationId);
	}

	/**
	 * creates a new, up-to-date Dijkstra
	 * @param sourceTeam source device's team
	 * @param gameLevel GameLevel object
	 * @return updated Dijkstra object
	 */
	private static DijkstraAlgorithm createDijkstra(final String sourceTeam, final GameLevel gameLevel) {
		List<Edge> edges = new ArrayList<>();
		for(Map.Entry<String, String> connection : gameLevel.getConnections()) {
			NetworkDevice a = gameLevel.getIdToDeviceObject().get(connection.getKey());
			NetworkDevice b = gameLevel.getIdToDeviceObject().get(connection.getValue());
			Integer weightA = 1; //TODO: calculate based on distance
			Integer weightB = 1;
			if (!a.getTeam().equals(sourceTeam)) {
				weightA = 999999;
			}
			if (!b.getTeam().equals(sourceTeam)) {
				weightB = 999999;
			}
			Edge edgeA = new Edge(b.getId(), a.getId(), weightA);
			Edge edgeB = new Edge(a.getId(), b.getId(), weightB);
			edges.add(edgeA);
			edges.add(edgeB);
		}
		return new DijkstraAlgorithm(new Graph(edges));
	}
}