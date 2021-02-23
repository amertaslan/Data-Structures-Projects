
public class AminoAcidNode {
	private String aminoAcidName;
	private AminoAcidNode down;
	private CodonNode right;
	
	public AminoAcidNode(String dataToAdd) {
		aminoAcidName = dataToAdd;
		this.down = null;
		this.right = null;
	}
	
	public String getAminoAcidName() {return aminoAcidName;}
	public AminoAcidNode getDown() {return down;}
	public CodonNode getRight() {return right;}

	public void setAminoAcidName(String aminoAcidName) {this.aminoAcidName = aminoAcidName;}
	public void setDown(AminoAcidNode down) {this.down = down;}
	public void setRight(CodonNode right) {this.right = right;}
}
