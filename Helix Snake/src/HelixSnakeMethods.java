import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

public class HelixSnakeMethods {
    char[][] canvas = new char[25][60];
    char[] letters = new char[4];
	MultiLinkedList CodonList;
	SingleLinkedList tempsnake;
    Random rnd = new Random();
    Snake snake = new Snake();
    Score sc = new Score();
    private int levelCounter = 0;
    private int time = 0;
    public KeyListener klis;
    public int rkey;    // key   (for press/release)
    public enigma.console.Console cn = Enigma.getConsole("Game", 250, 60, 35, 0);


    public HelixSnakeMethods() throws InterruptedException, IOException {
    	CodonList = new MultiLinkedList();
    	CodonList.ReadFile();
    	tempsnake = new SingleLinkedList();
        letters[0] = 'A';
        letters[1] = 'C';
        letters[2] = 'G';
        letters[3] = 'T';
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 60; j++) {
                canvas[i][j] = ' ';
            }
        }
        snake.addFirst(letters);
        snake.getSnake().update(canvas, snake);
        for (int i = 0; i < 25; i++) {
            canvas[i][0] = '#';
            canvas[i][59] = '#';
        }
        for (int i = 0; i < 60; i++) {
            canvas[0][i] = '#';
            canvas[24][i] = '#';
        }
        for (int i = 0; i < 3; i++) {
            addingRandomLetter();
        }
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
        printing();
        rightPanel();
        moving();
    }

    public void rightPanel() {
        Enigma.getConsole().getTextWindow().setCursorPosition(61,0 );
        System.out.println("Score: " + sc.getScore());
        Enigma.getConsole().getTextWindow().setCursorPosition(61, 1);
        System.out.println("----------");
        Enigma.getConsole().getTextWindow().setCursorPosition(61, 22);
        System.out.println("Level: " + levelCounter);
        Enigma.getConsole().getTextWindow().setCursorPosition(61, 23);
        System.out.println("Time: " + time);
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
    }

    boolean isAlive() {
        if ((canvas[snake.getX()][snake.getY()] == '#') || snake.getSnake().isCrashed()) {
            return false;
        }
        return true;
    }
    

    void moving() throws InterruptedException {//tu?lar olmad? netteki videodan bak bii.....
        int counter = 0;
        sc.setScore(sc.getScore()+snake.codonScoreFirst());
        while (isAlive()) {
            Thread.sleep(100);
            turn();
            snake.move();
            turn();

            if (isAlive()) {
                boolean isAteSomething=snake.getSnake().moveSnakeinSll(canvas, snake, letters);
                if (isAteSomething){
                    sc.addScore5();
                    sc.setScore(sc.getScore()+snake.codonScore());
                }
                snake.getSnake().update(canvas, snake);
                Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
                printing();
                rightPanel();
            }
            counter++;
            if (counter % 200 == 0) {
                addDiesis();
                levelCounter++;
            }
            if (counter % 10 == 0) {
                time++;
            }
        }
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 26);
        System.out.println("GAME OVER");
        System.out.println("Maybe Next Time");
        //Skor Hesaplan?lan yer
//        int howmany = snake.getSnake().size();
//        int total = (howmany - 3) * 5;
//        sc.setScore(total);
    }

    public void turn() {
        klis = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                rkey = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        if (rkey == KeyEvent.VK_RIGHT)
            snake.setDirection((snake.getDirection() - 1 + 4) % 4);
        else if (rkey == KeyEvent.VK_LEFT)
            snake.setDirection((snake.getDirection() + 1) % 4);
        cn.getTextWindow().addKeyListener(klis);
        rkey = 0;
    }

    public void printing() {
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 60; j++) {
                if (canvas[i][j]=='#'){
                    Enigma.getConsole().setTextAttributes(new TextAttributes(Color.RED));
                    System.out.print(canvas[i][j]);
                    Enigma.getConsole().setTextAttributes(new TextAttributes(Color.white));
                }
                else if (canvas[i][j]!=' '){
                   if (snake.getSnake().isSnake(i,j)){
                       Enigma.getConsole().setTextAttributes(new TextAttributes(Color.blue));
                       System.out.print(canvas[i][j]);
                       Enigma.getConsole().setTextAttributes(new TextAttributes(Color.white));
                   }
                   else{
                       Enigma.getConsole().setTextAttributes(new TextAttributes(Color.green));
                       System.out.print(canvas[i][j]);
                       Enigma.getConsole().setTextAttributes(new TextAttributes(Color.white));
                   }
                }
                else
                    System.out.print(canvas[i][j]);
            }
            System.out.println();
        }
    }


    public void addingRandomLetter() {
        int[] coordinates = selectingEmptyCoordinates();
        canvas[coordinates[0]][coordinates[1]] = letters[rnd.nextInt(4)];
    }

    public void addDiesis() {
        int[] coordinates = selectingEmptyCoordinates();
        Enigma.getConsole().setTextAttributes(new TextAttributes(Color.RED));
        canvas[coordinates[0]][coordinates[1]] = '#';
        Enigma.getConsole().setTextAttributes(new TextAttributes(Color.white));
    }

    private int[] selectingEmptyCoordinates() {
        int x, y;
        do {
            x = rnd.nextInt(23) + 1;
            y = rnd.nextInt(58) + 1;
        } while (canvas[x][y] != ' ');
        int[] retValues = new int[2];
        retValues[0] = x;
        retValues[1] = y;
        return retValues;
    }
}
