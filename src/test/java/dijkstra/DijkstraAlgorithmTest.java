package dijkstra;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DijkstraAlgorithmTest {

	private List<String> nodes;
	private List<Edge> edges;

	@Test
	public void testExecute() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			nodes.add("Node_" + i);
		}

		addLane(0, 1, 85);
		addLane(1, 0, 90);
		addLane(0, 2, 217);
		addLane(0, 4, 173);
		addLane(2, 6, 186);
		addLane(2, 7, 103);
		addLane(3, 7, 183);
		addLane(5, 8, 250);
		addLane(8, 9, 84);
		addLane(7, 9, 167);
		addLane(4, 9, 502);
		addLane(9, 10, 999999);
		addLane(1, 10, 600);
		addLane(10, 1, 999999);

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(nodes.get(10));
		List<String> path = dijkstra.getPath(nodes.get(0));

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (String vertex : path) {
			System.out.println(vertex);
		}

	}

	private void addLane(int sourceLocNo, int destLocNo,
						 int duration) {
		Edge lane = new Edge(nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
		edges.add(lane);
	}

}
