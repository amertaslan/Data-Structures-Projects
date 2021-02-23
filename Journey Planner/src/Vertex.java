package graphdeneme;

import java.util.ArrayList;

public class Vertex {
	private int id;
	private String name;
	private Vertex from;
	private int vehicleTypeId;
	private ArrayList<Edge> edges;
	private int total = Integer.MAX_VALUE;
	private boolean defineVertex = false;
	ArrayList<Edge> path;

	public Vertex(int id, String name, int vehicleTypeId) {
		this.id = id;
		this.name = name;
		this.from = null;
		this.vehicleTypeId = vehicleTypeId;
		edges = new ArrayList<Edge>();
		this.path = new ArrayList<Edge>();
	}

	public Vertex(int id){
		this.id = id;
		this.from = null;
		edges = new ArrayList<Edge>();
		this.path = new ArrayList<Edge>();
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(int vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String toString() {
		return "id: " + id;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setDefineVertex() {
		this.defineVertex = true;
	}
	
	public void resetDefineVertex() {
		this.defineVertex = false;
	}
	public boolean getDefineVertex() {
		return defineVertex;
	}

	public void setPath(ArrayList<Edge> path) {
		this.path = path;
	}

	public ArrayList<Edge> getPath() {
		return path;
	}
	
	public void setFullPath(ArrayList<Edge> path) {
		this.path = path;
	}
}
