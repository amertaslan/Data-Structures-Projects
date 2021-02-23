package graphdeneme;

public class Edge {
	private Vertex source;
	private Vertex destination;
	private int weight;
	private int direction;
	private int lineNo;
	
	public Edge(Vertex source, Vertex destination, int weight, int direction, int lineNo) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.direction = direction;
		this.lineNo = lineNo;
	}
	
	public Edge(Vertex source, Vertex destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.direction = -1; // -1 direction is undirected walk edges.
		this.lineNo = 0; // 0 line is walk line
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getlineNo() {
		return lineNo;
	}

	public void setlineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "source=" + source.getId() + ", destination=" + destination.getId() + ", weight=" + weight;
	}	
	
}
