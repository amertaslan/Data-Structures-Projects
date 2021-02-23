
public class CodonNode {
	private String codonName;
	private CodonNode next;
	
	public CodonNode(String dataToAdd) {
		codonName = dataToAdd;
		this.next = null;
	}

	public String getCodonName() {return codonName;}
	public CodonNode getNext() {return next;}

	public void setCodonName(String codonName) {this.codonName = codonName;}
	public void setNext(CodonNode next) {this.next = next;}	
}
