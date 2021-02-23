package graphdeneme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Management {
	private ArrayList<String> stops = new ArrayList<String>();;
	private ArrayList<String> distances = new ArrayList<String>();
	private ArrayList<String> trips = new ArrayList<String>();
	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<String> tests = new ArrayList<String>();
	private ArrayList<Edge> path = new ArrayList<Edge>();
	private ArrayList<Edge> reset = new ArrayList<Edge>();
	private Dijkstra dijkstra;
	private Graph graph = new Graph();
	private File file;
	private HashMap<Integer,Vertex> vertices;
	private HashMap<String,Edge> edges;
	private Vertex[] vertexArr;
	private Edge[] edgeArr;
	private String source;
	private String destination;

	public Management() throws IOException {
		System.out.println("Loading Map...");
		file = new File("Stop.txt");
		stops = readFile(file, stops);
		file = new File("Distance.txt");
		distances = readFile(file, distances);
		file = new File("Trip.txt");
		trips = readFile(file, trips);
		file = new File("Line.txt");
		lines = readFile(file, lines);
		file = new File("test_stops.txt");
		tests = readFile(file, tests);
		addNeighborEdge();
		addEdge();
		vertices = graph.getVertices();
		edges = graph.getEdges();
		vertexArr = new Vertex[vertices.size()];
		edgeArr = new Edge[edges.size()];
		Iterator<Vertex> vertexItr = graph.vertices().iterator();
		Iterator<Edge> edgeItr = graph.edges().iterator();
		int i = 0;
		while (vertexItr.hasNext()) {
			vertexArr[i] = vertexItr.next();
			i++;
		}
		i = 0;
		while (edgeItr.hasNext()) {
			edgeArr[i] = edgeItr.next();
			i++;
		}
		testDijkstra();
		dijkstra();
		//graph.print();
	}
	
	public void testDijkstra() {
		String[] testStops;
		for (String line : tests) {
			testStops = line.split(";");
			source = testStops[0];
			destination = testStops[1];
			System.out.println("Source: " + source);
			System.out.println("Destination: " + destination);
			for (int j = 0; j < vertexArr.length; j++) {
				vertexArr[j].resetDefineVertex();
				vertexArr[j].setFrom(null);
				vertexArr[j].setPath(reset);
				vertexArr[j].setTotal(Integer.MAX_VALUE);
			}
			dijkstra = new Dijkstra(vertexArr, edgeArr);
			try {
				dijkstra.getShortestPath(vertices.get(Integer.parseInt(source)), vertices.get(Integer.parseInt(destination)));
				dijkstra.printShortestPath(vertices.get(Integer.parseInt(source)), vertices.get(Integer.parseInt(destination)));
				path = dijkstra.getPath(vertices, destination);
				for (Edge edge : path) {
					if (edge.getlineNo()==0) {
						System.out.println(edge.getSource().getId() + "-" + edge.getDestination().getId() + " arasýnda " + edge.getWeight() + " metre yürü");
					}
					else {
						if (edge.getSource().getVehicleTypeId() == edge.getDestination().getVehicleTypeId() && edge.getSource().getVehicleTypeId()==1) {
							
						}
						System.out.println(edge.getSource().getId() + "-" + edge.getDestination().getId() + " arasýnda " + edge.getlineNo() + " numralý hattý kullan");
					}
				}
			} 
			catch (Exception e) {
				System.out.println("input error");
			}
			System.out.println();
		}
	}

	public void dijkstra() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.print("Source: ");
			source = scan.nextLine();
			System.out.print("Destination: ");
			destination = scan.nextLine();
			for (int j = 0; j < vertexArr.length; j++) {
				vertexArr[j].resetDefineVertex();
				vertexArr[j].setFrom(null);
				vertexArr[j].setPath(reset);
				vertexArr[j].setTotal(Integer.MAX_VALUE);
			}
			dijkstra = new Dijkstra(vertexArr, edgeArr);
			try {
				dijkstra.getShortestPath(vertices.get(Integer.parseInt(source)), vertices.get(Integer.parseInt(destination)));
				dijkstra.printShortestPath(vertices.get(Integer.parseInt(source)), vertices.get(Integer.parseInt(destination)));
				path = dijkstra.getPath(vertices, destination);
				for (Edge edge : path) {
					if (edge.getlineNo()==0) {
						System.out.println(edge.getSource().getId() + "-" + edge.getDestination().getId() + " arasýnda " + edge.getWeight() + " metre yürü");
					}
					else {
						System.out.println(edge.getSource().getId() + "-" + edge.getDestination().getId() + " arasýnda " + edge.getlineNo() + " numralý hattý kullan");
					}
				}
			} 
			catch (Exception e) {
				System.out.println("input error");
			}
		}
	}

	public ArrayList<String> readFile(File file, ArrayList<String> list) throws IOException {
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr); 
		String line = "";
		br.readLine();
		while((line=br.readLine())!=null) {
			list.add(line);
		}  
		fr.close(); 

		return list;
	}

	public void addNeighborEdge() {
		String[] sourceStop;
		String[] neighborStops;
		String[] stopDistances;
		String[] destinationStop;
		for (String fullLine : stops) {
			sourceStop = fullLine.split(";");
			neighborStops = sourceStop[5].split("\\.");
			for (int i = 0; i < neighborStops.length; i++) {
				stopDistances = neighborStops[i].split(":");
				for (String controlLine : stops) {
					destinationStop = controlLine.split(";");

					if (stopDistances[0].equals(destinationStop[0])) {
						graph.addNeighborEgde(Integer.parseInt(sourceStop[0]), sourceStop[1], Integer.parseInt(sourceStop[4]), Integer.parseInt(destinationStop[0]), destinationStop[1], Integer.parseInt(destinationStop[4]), Integer.parseInt(stopDistances[1]));
					}
				}
			}
		}
	}
	public void addEdge() {
		String[] distanceLines;
		String[] tripLines;
		String[] infoLines;
		for (String distance : distances) {
			distanceLines = distance.split(";");
			for (String trip : trips) {
				tripLines = trip.split(";");
				if (distanceLines[0].equals(tripLines[3])) {
					for (String line : lines) {
						infoLines = line.split(";");
						if (infoLines[0].equals(tripLines[0])) {
							graph.addEgde(Integer.parseInt(distanceLines[0]), Integer.parseInt(distanceLines[1]), Integer.parseInt(distanceLines[2]), Integer.parseInt(infoLines[1]), Integer.parseInt(tripLines[1]));
						}
					}
				}
			}
		}
	}
}
