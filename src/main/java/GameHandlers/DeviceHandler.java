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
	 * @return path in List<String> form
	 */
	public static List<String> getPath(final String sourceId, final String destinationId, final GameLevel gameLevel) {
		NetworkDevice source = gameLevel.getIdToDeviceObject().get(sourceId);
		DijkstraAlgorithm dijkstraAlgorithm = checkAlgorithmVersion(source, gameLevel);

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
		for(Map.Entry<Map.Entry<String, String>, Integer> connection : gameLevel.getConnections().entrySet()) {
			NetworkDevice a = gameLevel.getIdToDeviceObject().get(connection.getKey().getKey());
			NetworkDevice b = gameLevel.getIdToDeviceObject().get(connection.getKey().getValue());
			Integer weightA = connection.getValue();
			Integer weightB = connection.getValue();
			if (!a.getTeam().equals(sourceTeam)) {
				weightA += 999999;
			}
			if (!b.getTeam().equals(sourceTeam)) {
				weightB += 999999;
			}
			Edge edgeA = new Edge(b.getId(), a.getId(), weightA);
			Edge edgeB = new Edge(a.getId(), b.getId(), weightB);
			edges.add(edgeA);
			edges.add(edgeB);
		}
		return new DijkstraAlgorithm(new Graph(edges));
	}

	/**
	 * Checks device dijksttra algorithm map version, updates if necessary
	 * @param source Source device id
	 * @param gameLevel GameLevel object
	 * @return Dijkstra Algorithm
	 */
	private static DijkstraAlgorithm checkAlgorithmVersion(final NetworkDevice source, final GameLevel gameLevel) {
		DijkstraAlgorithm dijkstraAlgorithm;
		if(gameLevel.getMapVersion() > source.getMapVersion()) {
			dijkstraAlgorithm = createDijkstra(source.getTeam(), gameLevel);
			dijkstraAlgorithm.execute(source.getId());
			source.setDijkstra(dijkstraAlgorithm);
			source.setMapVersion(gameLevel.getMapVersion());
		} else {
			dijkstraAlgorithm = source.getDijkstra();
		}
		return dijkstraAlgorithm;
	}

	/**
	 * Returns the nearest enemy id to the given device
	 * @param source Source NetworkDevice
	 * @param gameLevel GameLevel object
	 * @return String id of nearest enemy
	 */
	public static String getNearestEnemy(final NetworkDevice source, final GameLevel gameLevel) {
		DijkstraAlgorithm dijkstraAlgorithm = checkAlgorithmVersion(source, gameLevel);
		List<String> enemyDevices = new ArrayList<>();
		for(Map.Entry<String, NetworkDevice> device : gameLevel.getIdToDeviceObject().entrySet()) {
			if(!device.getValue().getTeam().equals(source.getTeam())) {
				enemyDevices.add(device.getKey());
			}
		}
		if(enemyDevices.size() > 0) {
			List<String> path = dijkstraAlgorithm.getPath(enemyDevices.get(0));
			for (int i = 1; i < enemyDevices.size(); i++) {
				List<String> newPath = dijkstraAlgorithm.getPath(enemyDevices.get(i));
				if (newPath.size() < path.size()) {
					path = newPath;
				}
			}
			return path.get(path.size()-1);
		} else {
			return null;
		}
	}
}