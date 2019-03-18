package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra's Algorithm for finding shortest path, implemented in a way that it returns the path
 */
public class DijkstraAlgorithm {

	private final List<Edge> edges;
	private Set<String> settledNodes;
	private Set<String> unSettledNodes;
	private Map<String, String> predecessors;
	private Map<String, Integer> distance;

	public DijkstraAlgorithm(Graph graph) {
		// create a copy of the array so that we can operate on this array
		this.edges = new ArrayList<>(graph.getEdges());
	}

	/**
	 * Runs the algorithm for a given source node
	 * @param source starting node
	 */
	public void execute(String source) {
		settledNodes = new HashSet<>();
		unSettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			String node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * Looks for a shorter way to get to an adjacent node, if it finds one, set that as the shortest
	 * @param node node we are checking
	 */
	private void findMinimalDistances(String node) {
		List<String> adjacentNodes = getNeighbors(node);
		for (String target : adjacentNodes) {
			if (getNodeDistance(target) > getNodeDistance(node)
					+ getDistance(node, target)) {
				distance.put(target, getNodeDistance(node)
						+ getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}
	}

	/**
	 * Finds the given edge based off of its start/end points and returns its weight
	 * @param node edge start point
	 * @param target edge end point
	 * @return weight of edge
	 */
	private int getDistance(String node, String target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
					&& edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * Finds all unsettled neighbors of a node
	 * @param node given node
	 * @return unsettled neighbors of node
	 */
	private List<String> getNeighbors(String node) {
		List<String> neighbors = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
					&& !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	/**
	 *
	 * @param vertices unsettled nodes
	 * @return
	 */
	private String getMinimum(Set<String> vertices) {
		String minimum = null;
		for (String vertex : vertices) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getNodeDistance(vertex) < getNodeDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	/**
	 * checks if a given vertex is settled
	 * @param vertex vertex
	 * @return is the vertex settled
	 */
	private boolean isSettled(String vertex) {
		return settledNodes.contains(vertex);
	}

	/**
	 * returns the distance to a given node, if no distance is found set it to max value
	 * @param destination destination node
	 * @return distance to the node
	 */
	private int getNodeDistance(String destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * returns the path from the source to the target, null of no path exists
	 * @param target target node
	 * @return path in List<String> form
	 */
	public List<String> getPath(String target) {
		List<String> path = new ArrayList<>();
		String step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null; //TODO: throw exception?
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		path.remove(0);
		return path;
	}
}