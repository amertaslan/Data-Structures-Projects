
public class Computer {
	private int result;
	private int score;
	private String solution;
	private int best; 
	
	public Computer() {
		this.best = 0;
		this.result = 0;
		this.score = 0;
		this.solution = null;
	}
	
	public int getBest() {return best;}
	public int getResult() {return result;}
	public int getScore() {return score;}
	public String getSolution() {return solution;}
	
	public void setBest(int best) {this.best = best;}
	public void setResult(int result) {this.result = result;}
	public void setScore(int score) {this.score = score;}
	public void setSolution(String solution) {this.solution = solution;}
	
	
	
	
}
