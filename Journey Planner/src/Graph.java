package graphdeneme;

import java.util.HashMap;

public class Graph {
	private HashMap<Integer,Vertex> vertices;
	private HashMap<String,Edge> edges;
	private int count = 0;

	public Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
	}
	
	public HashMap<Integer,Vertex> getVertices() {
		return vertices;
	}

	public HashMap<String,Edge> getEdges() {
		return edges;
	}
	
	public void addNeighborEgde(int sourceId, String source, int sourcevehicleTypeId, int destinationId, String destination, int desVehicleTypeId, int weight) {

		if(edges.get(sourceId + "-" + destinationId) == null) 
		{
			Vertex source_v, destination_v;

			if(vertices.get(sourceId) == null) {
				source_v  = new Vertex(sourceId, source, sourcevehicleTypeId);
				vertices.put(sourceId, source_v);
			}
			else {
				source_v = vertices.get(sourceId);
			}

			if(vertices.get(destinationId) == null) {
				destination_v  = new Vertex(destinationId, destination, desVehicleTypeId);
				vertices.put(destinationId, destination_v );
			}
			else {
				destination_v = vertices.get(destinationId);
			}
			count++;
			Edge edge = new Edge(source_v, destination_v, weight);
			source_v.addEdge(edge);
			destination_v.addEdge(edge);
			edges.put(sourceId + "-" + destinationId, edge);
		}
		else {
			System.out.println("This edge has already added");
		}
	}
	public void addEgde(int sourceId, int destinationId, int weight, int lineNo, int direction) {

		if(edges.get(sourceId + "-" + destinationId + "-" + lineNo + "-" + direction) == null && edges.get(destinationId + "-" + sourceId + "-" + lineNo  + "-" + direction) == null) {
			Vertex source_v, destination_v;

			if(vertices.get(sourceId) == null) {
				source_v  = new Vertex(sourceId);
				vertices.put(sourceId, source_v);
			}
			else {
				source_v = vertices.get(sourceId);
			}

			if(vertices.get(destinationId) == null) {
				destination_v  = new Vertex(destinationId);
				vertices.put(destinationId, destination_v );
			}
			else {
				destination_v = vertices.get(destinationId);
			}
			
			count++;
			Edge edge = new Edge(source_v, destination_v, weight, direction, lineNo);
			source_v.addEdge(edge);
			destination_v.addEdge(edge);
			edges.put(sourceId + "-" + destinationId + "-" + lineNo + "-" + direction, edge);
		}
		else {
			//System.out.println("This edge has already added");
		}
	}

	public void print() {
		System.out.println("Source\tDestination\tWeight");
		for (Edge e : edges.values()) {
			System.out.println("" + e.getSource().getId() + "\t" + e.getDestination().getId() +  "\t" + e.getlineNo() +  "\t" + e.getDirection() +"\t\t" + e.getWeight()+ " ");
		}
		System.out.println(count);
	}
	public void printVertex() {
		System.out.println("Source\tDestination\tWeight");
		for (Vertex v : vertices.values()) {
			System.out.println(v.toString());
		}
	}

	public  Iterable<Vertex> vertices() {
		return vertices.values();
	}

	public  Iterable<Edge> edges() {
		return edges.values();
	}

	public int size() {
		return vertices.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
