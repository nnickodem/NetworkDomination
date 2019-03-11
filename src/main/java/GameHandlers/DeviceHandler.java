package GameHandlers;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Currently holds path creation for packets
 *
 * Algorithm ripped from:
 * https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
 */
public class DeviceHandler {

	private static final int NO_PARENT = -1;

	/**
	 * Handles the reading/conversion of the Dijkstra algorithm's results
	 * @param startID starting device ID
	 * @param endID target device ID
	 * @param gameLevel game level object
	 * @return shortest path from startID to endID as List<String>
	 */
	public static List<String> path(final String startID, final String endID, final GameLevel gameLevel) {
		Map<String, NetworkDevice> idToDeviceObject = gameLevel.getIdToDeviceObject();
		Map<Integer, String> indexToID = gameLevel.getIndexToId();
		if(idToDeviceObject.get(startID).getPaths() == null) {
			List<List<Integer>> paths = dijkstra(gameLevel.getAdjacencyMatrix(), idToDeviceObject.get(startID).getIndex());
			Map<String, List<String>> idPaths = new HashMap<>();
			for(List<Integer> path : paths) {
				List<String> idPath = new ArrayList<>();
				for(Integer hop : path) {
					idPath.add(indexToID.get(hop));
				}
				idPaths.put(idPath.get(idPath.size()-1), idPath);
			}
			idToDeviceObject.get(startID).setPaths(idPaths);
		}

		return idToDeviceObject.get(startID).getPath(endID);
	}

	/**
	 * Dijkstra's shortest path algorithm, determines the shortest path using an adjacency matrix
	 * @param adjacencyMatrix adjacency matrix as int[][]
	 * @param startVertex starting device index
	 * @return All shortest paths from the starting device as a List<List<Integer>>
	 */
	private static List<List<Integer>> dijkstra(int[][] adjacencyMatrix, int startVertex) {
		int nVertices = adjacencyMatrix[0].length;

		// shortestDistances[i] will hold the
		// shortest distance from src to i
		int[] shortestDistances = new int[nVertices];

		// added[i] will true if vertex i is
		// included / in shortest path tree
		// or shortest distance from src to
		// i is finalized
		boolean[] added = new boolean[nVertices];

		// Initialize all distances as
		// INFINITE and added[] as false
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		// Distance of source vertex from
		// itself is always 0
		shortestDistances[startVertex] = 0;

		// Parent array to store shortest
		// path tree
		int[] parents = new int[nVertices];

		// The starting vertex does not
		// have a parent
		parents[startVertex] = NO_PARENT;

		// Find shortest path for all
		// vertices
		for (int i = 1; i < nVertices; i++) {

			// Pick the minimum distance vertex
			// from the set of vertices not yet
			// processed. nearestVertex is
			// always equal to startNode in
			// first iteration.
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			// Mark the picked vertex as
			// processed
			added[nearestVertex] = true;

			// Update dist value of the
			// adjacent vertices of the
			// picked vertex.
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance +
							edgeDistance;
				}
			}
		}

		return generateSolution(startVertex, shortestDistances, parents);
	}

	/**
	 * Creates and returns a list of the shortest paths
	 * @param startVertex starting device's index
	 * @param distances distances (number of hops) to destination
	 * @param parents shortest path tree
	 * @return List of shortest paths as List<List<Integer>>
	 */
	private static List<List<Integer>> generateSolution(int startVertex, int[] distances, int[] parents) {
		int nVertices = distances.length;
		List<List<Integer>> paths = new ArrayList<>();

		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			if (vertexIndex != startVertex) {
				List<Integer> path = generatePath(vertexIndex, parents, new ArrayList<>());
				Collections.reverse(path);
				path.remove(0);
				paths.add(path);
			}
		}
		return paths;
	}

	/**
	 * Recursively generates and returns one of the shortest paths
	 * @param currentVertex current vertex
	 * @param parents shortest path tree
	 * @param path path so far
	 * @return path as List<Integer>
	 */
	private static List<Integer> generatePath(int currentVertex, int[] parents, List<Integer> path) {
		if (currentVertex == NO_PARENT) {
			return path;
		}
		path.add(currentVertex);
		return generatePath(parents[currentVertex], parents, path);
	}
}