import java.io.IOException;
import java.util.Random;

import enigma.core.Enigma;

public class Snake {
    private int x, y;
    private int direction = 0;
    private int lastx = 0;
    private int lasty = 0;
    private int counter = 2;
    Random rnd = new Random();
    private SingleLinkedList snake = new SingleLinkedList();
    MultiLinkedList CodonList;
    SingleLinkedList tempsnake = new SingleLinkedList();


    public Snake() throws IOException {
    	CodonList = new MultiLinkedList();
    	CodonList.ReadFile();
        x = 10;
        y = 25;
        direction = 1;
        lastx = x;
        lasty = y;
    }

    public int codonScore() {
    	String codon;
    	int score = 0;
    	if (snake.size() % 3 == 0) {
			codon = snake.findCodon();
			score = CodonList.search(codon);
			Enigma.getConsole().getTextWindow().setCursorPosition(61, counter);
	        System.out.println(codon + " " + score);
	        counter++;
		}
		return score;
    }
    
    public int codonScoreFirst() {
    	String codon;
    	int score = 0;
    	if (snake.size() % 3 == 0) {
			codon = snake.findCodon();
			score = CodonList.search(codon);
		}
		return score;
    }

    public void addFirst(char[] letters) {
        for (int i = 0; i < 3; i++) {
            addToSnake(letters[rnd.nextInt(4)], lastx, lasty);
            lasty--;
        }
        codonScore();
    }


    public SingleLinkedList getSnake() {
        return snake;
    }

    public void setSnake(SingleLinkedList snake) {
        this.snake = snake;
    }

    public int getLastx() {
        return lastx;
    }

    public void setLastx(int lastx) {
        this.lastx = lastx;
    }

    public void addLastx() {
        this.lastx++;
    }

    public void subLastx() {
        this.lastx--;
    }

    public void addLasty() {
        this.lasty++;
    }

    public void subLasty() {
        this.lasty--;
    }

    public int getLasty() {
        return lasty;
    }

    public void setLasty(int lasty) {
        this.lasty = lasty;
    }

    public void addToSnake(char dataToAdd, int lastx, int lasty) {
        snake.addToEnd(dataToAdd, lastx, lasty);
    }

    public void addToSnakeFront(char dataToAdd, int lastx, int lasty) {
        snake.addFront(dataToAdd, lastx, lasty, direction);
    }

    public char[] indexOfSnake() {//snake içini canvasa bas update et yarým satniyede bir dönmeye bak
        return snake.display().toCharArray();
    }

    public void move() {
        if (direction == 1) {
            y++;
        } else if (direction == 2) {
            x--;
        } else if (direction == 3) {
            y--;
        } else if (direction == 0) {
            x++;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
