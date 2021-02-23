import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import enigma.core.Enigma;

public class DoubleLinkedList {
    private DoubleLinkedListNode head;
    private DoubleLinkedListNode tail;

    public DoubleLinkedList() {

        head = null;
        tail = null;
    }

    public void InsertPosition(String Name, int dataToAdd) {
        DoubleLinkedListNode newNode = new DoubleLinkedListNode(Name, dataToAdd);
        if (head == null & tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            DoubleLinkedListNode temp = head;
            if (dataToAdd>(int)head.getData()){
                newNode.setNext(head);
                newNode.setPrev(null);
                head=newNode;
            }
            else{
                while (temp.getNext() != null && dataToAdd < (Integer) temp.getNext().getData()) {
                    temp = temp.getNext();
                }
                newNode.setPrev(temp);
                newNode.setNext(temp.getNext());

                if (temp.getNext() != null) {
                    temp.getNext().setPrev(newNode);
                    temp.setNext(newNode);
                } else {
                    tail = newNode;
                    temp.setNext(newNode);
                }
            }


        }

    }

    public void DeleteLastOne() {
        if (head == null)
            System.out.println("Empty");

        if (head.getNext() == null)
            System.out.println("Empty");

        // Find the second last node
        DoubleLinkedListNode second_last = head;
        while (second_last.getNext().getNext() != null)
            second_last = second_last.getNext();

        // Change next of second last
        second_last.setNext(null);
    }

    public void DisplayOnScreeen() {  //Ekrana Yazd�r�lan B�l�m(Dosyadki veriler yazd�r�l�r
        //Node current will point to head
        DoubleLinkedListNode current = head;

        String[] PrintName = new String[size()];
        int[] PrintScore = new int[size()];
        int i = 0;
        while (current != null) {
            //Prints each node by incrementing the pointer.
            PrintName[i] = current.getName();
            PrintScore[i] = (int) current.getData();
            i++;
            current = current.getNext();
        }

        int k = 10;
        if (PrintName.length == 0) {
            System.out.println("Score table is empty.");
        } else {
            Enigma.getConsole().getTextWindow().setCursorPosition(10, 0);
            System.out.println("   Player Name");
            Enigma.getConsole().getTextWindow().setCursorPosition(30, 0);
            System.out.println("Score");
            for (int j = 1; j <= PrintName.length; j++) {
                if (j < 11) {
                    Enigma.getConsole().getTextWindow().setCursorPosition(k, j);
                    System.out.println(j + "| " + PrintName[j - 1]);
                }
            }
        }
        int l = 30;
        if (PrintScore.length != 0) {
            for (int j = 1; j <= PrintScore.length; j++) {
                if (j < 11) {
                    Enigma.getConsole().getTextWindow().setCursorPosition(l, j);
                    System.out.println("=>>" + PrintScore[j - 1]);
                }
            }
        }
    }


    public void AssignWriterMethod() {
        //Node current will point to head
        DoubleLinkedListNode current = head;
        System.out.println(size());
        String s = " ";
        if (head == null) {
            System.out.println("List is empty");
        }
        while (current != null) {
            //Prints each node by incrementing the pointer.
            s = (String) current.getName() + ";" + Integer.toString((int) current.getData());
            current = current.getNext();
        }
        Writing(s);
    }

    private void Writing(String metin) {
        try {
            File dosya = new File("score.txt");
            FileWriter yazici = new FileWriter(dosya, true);
            BufferedWriter yaz = new BufferedWriter(yazici);
            if (metin != null) {
                yaz.write(metin);
                yaz.newLine();
            }
            yaz.close();
            System.out.println("File Created Succesfully");

            ReadFile();
        } catch (Exception hata) {
            hata.printStackTrace();
        }
    }


    public String[] inputLine = new String[1000];

    public boolean ReadFile() throws IOException//score.txt den al�nan veriler  okutulur
    {
        ////
        int i = 0;
        if (!Files.exists(Paths.get("score.txt"))) {
            System.out.println("Score table is empty.");
            return false;
        } else {
            FileReader fileReader = new FileReader("score.txt");
            String line;
            BufferedReader bRead = new BufferedReader(fileReader);
            while ((line = bRead.readLine()) != null) {
                inputLine[i] = line;             // inputline contain input's every line
                i++;
            }
            bRead.close();
            int j = 0;
            while (j < inputLine.length && inputLine[j] != null) {
                String[] parts = inputLine[j].split(";", -1);
                InsertPosition(parts[0], Integer.parseInt(parts[1]));//burada b�y�kten k����e s�ralanabilmesi i�in method cagr�l�r
                j++;
            }
            return true;
        }

    }

    public int size() {
        int count = 0;
        if (head == null) {
        } else {
            DoubleLinkedListNode temp = head;
            while (temp != null) {
                count++;
                temp = temp.getNext();
            }
        }
        return count;
    }


}
