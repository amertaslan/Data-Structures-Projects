package data_structures_hw_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Management {
	private DoubleHashTable<Integer, ArrayList<Node>> table = new DoubleHashTable<Integer, ArrayList<Node>>();
	private String DELIMITERS;
	private ArrayList<String> stopWords = new ArrayList<String>();
	private ArrayList<Node> value = null;
	private String searchLine = null;
	private String[] searchArray = null;

	public Management() throws IOException {
		long indexingStartTime = System.currentTimeMillis();
		DELIMITERS = delimeters();
		stopWords = stopWordsReading(stopWords);
		searchLine = searchWordsReading(searchLine);
		searchArray = searchLine.split(DELIMITERS);
		String[] splitted = null;
		String line;
		File folderBBC = new File("bbc"); //finding all files in bbc file
		for (File fileBBCEntry : folderBBC.listFiles()) { 
			for (File fileEntry : fileBBCEntry.listFiles()) { //finding all txt files in files of bbc
				System.out.println(fileEntry.toString());
				try {  
					//					System.out.println(fileEntry.toString());
					FileReader fr=new FileReader(fileEntry);   //reads the finding files
					BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
					String lines = null;
					while((line=br.readLine())!=null) {
						lines = lines + " " + line;
					}
					fr.close();    //closes the stream and release the resources
					splitted = lines.split(DELIMITERS);
					for (int i = 0; i < splitted.length; i++) {
						for (String stopWord : stopWords) {
							if (splitted[i].equalsIgnoreCase(stopWord)) {
								splitted[i] = " ";
							}
						}
						splitted[i] = splitted[i].toLowerCase(Locale.ENGLISH);
					}

					for (int i = 0; i < splitted.length; i++) {				
						int counter = 0;
						String word = splitted[i];
						int key = SSFcalculateKey(splitted[i]);
						if (word!=null && !word.equals("") && !word.equals(" ")) {
							for (int j = 0; j < splitted.length; j++) {
								if (word.equalsIgnoreCase(splitted[j])) {
									counter++;
								}
							}
							for (int j = 0; j < splitted.length; j++) {
								if (word.equalsIgnoreCase(splitted[j])) {
									splitted[j] = "";
								}
							}
							if (counter>0) {

								ArrayList<Node> tempTable = (ArrayList<Node>)table.get(key, word);
								if (tempTable != null) {
									Node node = new Node(fileEntry.toString(), counter);
									tempTable.add(node);
									table.setValue(key, word, tempTable);
									counter = 0;
								}
								else {
									value = new ArrayList<Node>();
									Node keyNode = new Node(word);
									value.add(keyNode);
									Node node = new Node(fileEntry.toString(), counter);
									value.add(node);

									table.put(key, value);
									counter = 0;
								}
							}
						}
					}
				}
				catch(IOException e) {
					throw new FileNotFoundException();
				}
			}
		}
		long indexingEndTime = System.currentTimeMillis();
		//-------> open if you want to display values
		//table.display();  
		System.out.println("Unique word number: " + table.size());
		System.out.println("Last table size: " + table.capacity);
		System.out.println("Collusion counter: " +  table.collusionCounter());
		System.out.println("Indexing Time: " + (indexingEndTime-indexingStartTime) + " miliseconds");
		System.out.println();

		fileSearch();  //to search file input
		inputSearch();  //to search user input
	}

	//---------------------FILE SEARCH--------------------------------
	public void fileSearch() {
		for (int i = 0; i < searchArray.length; i++) {
			searchArray[i].toLowerCase(Locale.ENGLISH);
			for (String stopWord : stopWords) {
				if (searchArray[i].equals(stopWord)) {
					searchArray[i] = " ";
				}
			}
		}
		search(searchArray);
	}

	//---------------------INPUT SEARCH--------------------------------
	public void inputSearch() {
		System.out.println();
		System.out.println("Search your words...");
		System.out.println("Write i want to quit, if you want to quit.");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while (!input.equals("i want to quit")) {
			String[] inputArray = input.split(DELIMITERS);
			for (int i = 0; i < inputArray.length; i++) {
				inputArray[i] = inputArray[i].toLowerCase(Locale.ENGLISH);
				for (String word : stopWords) {
					if (inputArray[i].equals(word)) {
						inputArray[i] = " ";
					}
				}
			}
			search(inputArray);
			input = scan.nextLine();
		}
	}
	//need to change key value.
	//----------------------------------------SEARCH METHOD----------------------------------------------
	public void search(String[] searchArray) {
		long searchStartTime = 0;
		long searchEndTime  = 0;
		long timeDifference = 0;
		long timeDifferenceSum = 0;
		long averageSearchTime = 0;
		long minSearchTime = Integer.MAX_VALUE;
		long maxSearchTime = 0;
		for (int i = 0; i < searchArray.length; i++) {
			int findCounter = 0;
			if (!searchArray[i].equals(" ")) {
				System.out.println();
				ArrayList<Node> tempTable = null;
				searchStartTime = System.nanoTime();
				try {
					tempTable = (ArrayList<Node>)table.search(SSFcalculateKey(searchArray[i]), searchArray[i]);
					findCounter = tempTable.size()-1;
				} catch (Exception e) {
					System.out.println(searchArray[i] + " not found!!!");
				}
				searchEndTime = System.nanoTime();
				timeDifference = (searchEndTime-searchStartTime);
				if (timeDifference<minSearchTime) {
					minSearchTime = timeDifference;
				}
				if (timeDifference>maxSearchTime) {
					maxSearchTime = timeDifference;
				}
				timeDifferenceSum += timeDifference;
				try {
					System.out.println(tempTable.get(0).getName());
					System.out.println(findCounter + " document(s) found.");
					for (Node node : tempTable) {
						if (node.getCounter()!=0) {
							System.out.print(node.getName() + " " + node.getCounter() + ",  ");
						}
					}
					System.out.println();
					System.out.println("Search time: " + timeDifference + " nanoseconds");
				} catch (Exception e) {
					System.out.println(searchArray[i] + " not found!!!");
				}
			}
		}
		averageSearchTime = timeDifferenceSum/searchArray.length;
		System.out.println();
		System.out.println("Average search time: " + averageSearchTime + " nanoseconds");
		System.out.println("Min search time: " + minSearchTime + " nanoseconds");
		System.out.println("Max search time: " + maxSearchTime + " nanoseconds");
	}

	//---------------------------------------STOP WORDS READING-----------------------------------------------
	public ArrayList<String> stopWordsReading(ArrayList<String> stopWords) throws IOException {
		String line1;
		File file=new File("stop_words_en.txt");
		FileReader fr1=new FileReader(file);   //reads the finding files
		BufferedReader br1=new BufferedReader(fr1);  //creates a buffering character input stream   
		while((line1=br1.readLine())!=null) {
			stopWords.add(line1);
		}  
		fr1.close();    //closes the stream and release the resources  
		return stopWords;
	}

	//---------------------------------------SEARCH FILE READING---------------------------------------------
	public String searchWordsReading(String searchLine) throws IOException {
		String line1;
		File file=new File("1000.txt");
		FileReader fr1=new FileReader(file);   //reads the finding files
		BufferedReader br1=new BufferedReader(fr1);  //creates a buffering character input stream   
		while((line1=br1.readLine())!=null) {
			searchLine = searchLine + " " + line1;
		}  
		fr1.close();    //closes the stream and release the resources  
		return searchLine;
	}

	//-------------------------------------PAF CALCULATE METHODS----------------------------------------------------
	public int PAFcalculateKey(String word) {
		String letters = "abcdefghijklmnopqrstuvwxyz";
		int i =0;
		int sum =0;
		int factor = 33;
		String reverse = new StringBuffer(word).reverse().toString();
		char lettersChar[] = letters.toCharArray();
		char wordChar[] = reverse.toCharArray();
		for (int j = 0; j < wordChar.length; j++) {
			i=0;
			for (int k = 0; k < lettersChar.length; k++) {
				if(lettersChar[k] == wordChar[j]) {
					i++;
					break;
				}
				else
					i++;
			}
			sum = sum*factor;
			sum += i;
		}
		sum = sum % 67409;
		if (sum<0) {
			sum = sum + 67409;
		}
		return sum;
	}

	//-------------------------------------SSF CALCULATE METHODS----------------------------------------------------
	public int SSFcalculateKey(String value) {
		int tempKey = 0;
		for (int i = 0; i < value.length(); i++) {
			tempKey += calculateChar(value.charAt(i));
		}
		return tempKey;
	}
	public int calculateChar(char character) {
		int temp = 0;
		try {
			for (int i = 97; i < 123; i++) {
				if ((char)i == character) {
					temp = i-96;
				}
			}
		} catch(Exception e) {
			System.out.println("Illegal character exception!!!");
		}
		return temp;
	}

	//------------------------------------------DELIMETERS------------------------------------------------
	public String delimeters() {
		return "[-+=" +
				" " +        //space
				"\r\n " +    //carriage return line fit
				"1234567890" + //numbers
				"’'\"" +       // apostrophe
				"(){}<>\\[\\]" + // brackets
				":" +        // colon
				"," +        // comma
				"‒–—―" +     // dashes
				"…" +        // ellipsis
				"!" +        // exclamation mark
				"." +        // full stop/period
				"«»" +       // guillemets
				"-‐" +       // hyphen
				"?" +        // question mark
				"‘’“”" +     // quotation marks
				";" +        // semicolon
				"/" +        // slash/stroke
				"⁄" +        // solidus
				"␠" +        // space?
				"·" +        // interpunct
				"&" +        // ampersand
				"@" +        // at sign
				"*" +        // asterisk
				"\\" +       // backslash
				"•" +        // bullet
				"^" +        // caret
				"¤¢$€£¥₩₪" + // currency
				"†‡" +       // dagger
				"°" +        // degree
				"¡" +        // inverted exclamation point
				"¿" +        // inverted question mark
				"¬" +        // negation
				"#" +        // number sign (hashtag)
				"№" +        // numero sign ()
				"%‰‱" +    // percent and related signs
				"¶" +        // pilcrow
				"′" +        // prime
				"§" +        // section sign
				"~" +        // tilde/swung dash
				"¨" +        // umlaut/diaeresis
				"_" +        // underscore/understrike
				"|¦" +       // vertical/pipe/broken bar
				"⁂" +        // asterism
				"☞" +        // index/fist
				"∴" +        // therefore sign
				"‽" +        // interrobang
				"※" +       // reference mark
				"]";
	}
}
