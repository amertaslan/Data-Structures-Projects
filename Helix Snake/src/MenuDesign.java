import java.io.IOException;
import java.util.Scanner;

import enigma.core.Enigma;

public class MenuDesign {
    void Menu() throws InterruptedException, IOException {
        Enigma.getConsole("Game", 100, 45, 18, 0);

        int option = 0;
        do {
            Enigma.getConsole().getTextWindow().setCursorPosition(30, 0);
            System.out.println("--- HELIX SNAKE----");
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 1);
            System.out.println("1)PLAY");
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 2);
            System.out.println("2)SCOREBOARD");
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 3);
            System.out.println("3)EXIT");
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 4);
            System.out.println("Your Choice");
            Scanner scn = new Scanner(System.in);
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 5);
            option = scn.nextInt();

            if (option == 1) {
                clearScreen();
                HelixSnakeMethods manage = new HelixSnakeMethods();
                Score sc = new Score();
                sc.input();
                Thread.sleep(1000);
                clearScreen();
            } else if (option == 2) {
                DoubleLinkedList m = new DoubleLinkedList();
                clearScreen();
                if (m.ReadFile()) {
                    m.DisplayOnScreeen();
                }
                for (int i = 5; i > 0; i--) {
                    Enigma.getConsole().getTextWindow().setCursorPosition(45, 15);
                    System.out.println("Returning menu in "+i);
                    Thread.sleep(1000);
                }
                clearScreen();
            }

        } while (option != 3);

    }
    void clearScreen() {
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
        for (int i = 0; i < 44; i++) {
            for (int j = 0; j < 99; j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
        Enigma.getConsole().getTextWindow().setCursorPosition(0, 0);
    }
}

