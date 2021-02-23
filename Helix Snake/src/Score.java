import java.util.Scanner;

public class Score {
private static int Score=0;
public int getScore() {
	return Score;
}
public void setScore(int score) {
	Score = score;
}
public void addScore5() {
	Score+=5;
}

	public Score() {
	}

	DoubleLinkedList ss=new DoubleLinkedList();

	void input() {
		
		@SuppressWarnings("resource")
		Scanner scn=new Scanner(System.in);
		System.out.print("Please Enter Your Name: ");
		String playerName=scn.nextLine();
		ss.InsertPosition(playerName,Score);
		ss.AssignWriterMethod();
		setScore(0);
	}
	
	

}
