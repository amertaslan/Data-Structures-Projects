package data_structures_hw_1;

public class Node {
	private String name;
	private int counter;
	
	public Node(String name, int counter ) {
		this.name = name;
		this.counter = counter;
	}

	public Node(String name) {
		this.name = name;
		this.counter = 0;
	}

	public String getName() {return name;}
	public int getCounter() {return counter;}
	public void setName(String name) {this.name = name;}
	public void setCounter(int counter) {this.counter = counter;}
	
	public String toString() {
		if (counter>0) {
			return "    Text File--> name=" + name + ", counter=" + counter;
		}
		else {
			return "Word---> name=" + name;
		}
		
	}
}
