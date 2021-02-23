
public class Player {
	private int Result;
	private int Score;
	private String Solution;
	
	public Player() {
		this.Result = 5;
		this.Score = 0;
		this.Solution = null;
	}

	public int getResult() {return Result;}
	public int getScore() {return Score;}
	public String getSolution() {return Solution;}
	
	public void setResult(int result) {Result = result;}
	public void setScore(int score) {Score = score;}
	public void setSolution(String solution) {Solution = solution;}
	
}
