import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MultiLinkedList {
	private AminoAcidNode head;

	public MultiLinkedList() {
		this.head = null;
	}

	public void addAminoAcid(String dataToAdd) {
		if (head == null) {
			AminoAcidNode newnode = new AminoAcidNode(dataToAdd);
			head = newnode;
		}
		else {
			AminoAcidNode temp = head;
			while (temp.getDown() != null) {
				temp = temp.getDown();
			}
			AminoAcidNode newnode = new AminoAcidNode(dataToAdd);
			temp.setDown(newnode);
		}
	}

	public void addCodon(String aminoAcid, String codon) {
		if (head == null) {
			System.out.println("Add a amino acid before codon.");
		}
		else {
			AminoAcidNode temp = head;
			while (temp != null) {
				if (aminoAcid.equals(temp.getAminoAcidName())) {
					CodonNode temp2 = temp.getRight();
					if (temp2 == null) {
						CodonNode newnode = new CodonNode(codon);
						temp.setRight(newnode);
					}
					else {
						while (temp2.getNext() != null) {
							temp2 = temp2.getNext();
						}
						CodonNode newnode = new CodonNode(codon);
						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}

	public int sizeAminoAcid() {
		int count = 0;
		if (head == null) {
			System.out.println("Linked List is empty.");
		}
		else {
			AminoAcidNode temp = head;
			while (temp != null) {
				count++;
				temp = temp.getDown();
			}
		}
		return count;
	}

	public void display() {
		if (head == null) {
			System.out.println("Linked List is empty.");
		}
		else {
			AminoAcidNode temp = head;
			while (temp != null) {
				System.out.print(temp.getAminoAcidName() + " --> ");
				CodonNode temp2 = temp.getRight();
				while (temp2 != null) {
					System.out.print(temp2.getCodonName() + ", ");
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}

	public  String[] inputLine = new String[100];
	public  void ReadFile() throws IOException {
		int i = 0;
		FileReader fileReader = new FileReader("aminoacids.txt");
		String line;
		BufferedReader bRead = new BufferedReader(fileReader);
		while((line = bRead.readLine()) != null) {		
			inputLine[i] = line;             // inputline contain input's every line 		
			i++;
		}
		bRead.close();
		int j = 0;
		while (j < inputLine.length && inputLine[j] != null) {
			String[] parts = inputLine[j].split(",",-1);
			j++;
			addAminoAcid(parts[0]);
			for (int k = 1; k < parts.length; k++) {
				addCodon(parts[0], parts[k]);
			}
		}
	}
	
	public int search(String codon) {
		AminoAcidNode temp = head;
		String[] Codon = null;
		int score = 0;
		while (temp != null) {
			CodonNode temp2 = temp.getRight();
			while (temp2 != null) {
				Codon = temp2.getCodonName().split("-");
				if (Codon[0].equals(codon)) {
					score = Integer.valueOf(Codon[1]);
				}
				temp2 = temp2.getNext();
			}
			temp = temp.getDown();
		}
		return score;
	}
	
}
