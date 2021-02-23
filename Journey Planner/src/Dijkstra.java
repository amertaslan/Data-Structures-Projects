package graphdeneme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Dijkstra {
	private Edge[] edges;
	private ArrayList<Vertex> list = new ArrayList<Vertex>();

	public Dijkstra(Vertex[] vertices, Edge[] edges) {
		this.edges = edges;	
		for (int i = 0; i < vertices.length; i++) {
			list.add(vertices[i]);
		}
	}

	public void getShortestPath(Vertex source, Vertex destination) {
		while (source.getDefineVertex() == false) {
			setPoint(source);
			for (int i = 0; i < edges.length; i++) {
				if (source.equals(edges[i].getSource()) && source.getFrom() != (edges[i].getDestination())) {
					if (source.getTotal() + edges[i].getWeight() < edges[i].getDestination().getTotal()) {
						ArrayList<Edge> path = new ArrayList<Edge>();
						edges[i].getDestination().setTotal(source.getTotal() + edges[i].getWeight());
						edges[i].getDestination().setFrom(source);
						path.add(edges[i]);
						edges[i].getDestination().setPath(path);
					}
				}
			}
			source.setDefineVertex();
			source = movePoint(source, destination);
		}
	}

	public int setPoint(Vertex from) {
		if (from.getTotal() == (int)Double.POSITIVE_INFINITY || from.getTotal() < 0) {
			from.setTotal(0);
		}
		return from.getTotal();
	}

	public Vertex movePoint(Vertex source, Vertex destination) {
		Vertex movePoint = source;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDefineVertex() == false) {
				movePoint = list.get(i);
				break;
			}
		}
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).getDefineVertex() == false) {
				if (list.get(j).getTotal() < movePoint.getTotal()) {
					movePoint = list.get(j);
				}
			}
		}
		return movePoint;
	}

	public void printShortestPath(Vertex source, Vertex destination) {
		System.out.println("Shortest distance (" + source.getId() + " ... " + destination.getId() + ") : " + destination.getTotal());
	}
	
	public ArrayList<Edge> getPath(HashMap<Integer, Vertex> vertices, String destination) {
		Vertex temp = vertices.get(Integer.parseInt(destination));
		ArrayList<Edge> path = new ArrayList<Edge>();
		path = temp.getPath();
		while (temp.getFrom()!=null) {
			temp = temp.getFrom();
			ArrayList<Edge> temppath = temp.getPath();
			for (Edge edge : temppath) {
				path.add(edge);
			}
		}
		Collections.reverse(path);
		return path;
	}
	
	public ArrayList<Vertex> getVertices(HashMap<Integer, Vertex> vertices, String destination) {
		Vertex temp = vertices.get(Integer.parseInt(destination));
		ArrayList<Vertex> vertexPath = new ArrayList<Vertex>();
		vertexPath.add(temp);
		while (temp.getFrom()!=null) {
			temp = temp.getFrom();
			vertexPath.add(temp);
		}
		Collections.reverse(vertexPath);
		return vertexPath;
	}

	public ArrayList<Vertex> setList(Vertex source, Vertex destination) {
		list.add(0, destination);
		while (destination != null) {
			list.add(0, destination);
			destination = destination.getFrom();
		}	
		return list;
	}
}
