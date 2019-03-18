package dijkstra;

public class Edge  {

	private final String source;
	private final String destination;
	private final int weight;

	public Edge(String source, String destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getDestination() {
		return destination;
	}

	public String getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source + " " + destination;
	}
}
