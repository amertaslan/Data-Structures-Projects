import java.util.Random;
import java.util.Scanner;

public class NumbersMethods {
	private int[] onetoninearray;
	private int[] controlarray;
	private int onetonine;
	private int bignumbercontrol;
	private int bignumber;
	private int TargetNumber;
	private Player player;
	private Computer computer;
	private Stack solutionstack;
	private Stack solutionstacktemp;
	private Stack solutionstacktemp2;
	private CircularQueue computerQueue;
	private CircularQueue computerQueue2;

	public NumbersMethods() throws Exception 
	{
		Game myGame = new Game();
		Random random = new Random();	
		Scanner scan = new Scanner(System.in);
		this.onetonine = 0;
		this.onetoninearray = new int[6];
		this.controlarray = new int[6];
		this.bignumber = 0;
		this.player = new Player();
		this.computer = new Computer();
		this.solutionstack = new Stack(25);
		this.solutionstacktemp = new Stack(25);
		this.solutionstacktemp2=new Stack(25);
		this.computerQueue=new CircularQueue(10000);
		this.computerQueue2=new CircularQueue(10000);
		boolean flag = true;
		int round=1;

		while (flag) { //main game loop, controlling with a flag. 
			System.out.println("\n--- Round "+ round +" --------------------");
			bignumbercontrol = random.nextInt(4);
			TargetNumber = random.nextInt(900)+100;//assigning targetnumber as random between 100-999.
			RandomNumbersAssignment(random); //assigning random numbers
			PrintRandoms(); //printing the random numbers to the screen
			System.out.print("\nDuration: ");
			Countdown(); //processing 30 seconds countdown.

			ResultPrint(scan); //printing the result screen.
			ResultControl();//checking results for players to define who is closer to the target number.
			ScoreControl();//checking and increasing score.
			PrintScore();//printing the scores of the player to the screen.
			flag = FlagControl(); //controlling scores to define whether game finished or not finished.
			round++;
		}
		WinnerPrint(); //to print the winner.
	}

	public void WinnerPrint() {
		//controlling the possible game ending situation to define the winner.
		if (computer.getScore()>=100 && player.getScore()<computer.getScore()) {
			System.out.println("Computer won the game.");
		}
		else if (player.getScore()>=100 && computer.getScore()<player.getScore()) {
			System.out.println("Player won the game.");
		}
		else if (player.getScore()>=100 && computer.getScore()>=100 && player.getScore()==computer.getScore()) {
			System.out.println("The result is draw.");
		}
	}
	
	public boolean FlagControl() {
		//controlling player and computer score to finish the game.
		if (player.getScore()>=100 || computer.getScore()>=100) {
			return false; //if at least one player's score is more than or equals hundred, game finishes.
		}
		else {
			return true; //at all else situations game continues.
		}
	}

	public void RandomNumbersAssignment(Random random) {
		switch (bignumbercontrol) { //assignment of big random number.
		case 0: bignumber = 25; break;
		case 1: bignumber = 50; break;
		case 2: bignumber = 75; break;
		case 3: bignumber = 100; break;
		}
		for (int i = 0; i < onetoninearray.length - 1; i++) { //assignment of other digits.
			onetonine = random.nextInt(9)+1;
			onetoninearray[i] = onetonine;
		}
		onetoninearray[5] = bignumber;

		for (int i = 0; i < controlarray.length - 1; i++) {
			controlarray[i]=onetoninearray[i];
		}
		controlarray[5] = bignumber;
	}

	public void PrintRandoms() {
		System.out.println("\nTarget Number: " + TargetNumber);
		System.out.print("\nNumbers: ");
		for (int i = 0; i < onetoninearray.length; i++) {
			System.out.print(" " + onetoninearray[i]);
		}
		System.out.println("");
	}

	public void Countdown() { //30 second countdown	
		int countdown = 30;
		long time1 = 0;
		long time2 = System.currentTimeMillis();
		do {
			time1 = System.currentTimeMillis();
			if(time1 - time2 >= 1000) 
			{
				System.out.print(countdown-- + " ");
				time2 = time1;
			}
			computer.setResult(ComputerAI(onetoninearray)); //processing computer ai during countdown.
		} while(countdown >= 0);	
		System.out.println("\n\n--------------------------------");
	}

	public void ResultPrint(Scanner scan) {
		System.out.print("\nPlayer result: ");
		player.setResult(scan.nextInt());
		System.out.print("\nComputer result: ");
		System.out.print(computer.getResult());
		System.out.println("");
		System.out.println("");
	}

	public void ResultControl() {
		//checking result to define which player is more closer to the target number.
		if (Math.abs(player.getResult()-TargetNumber) < Math.abs(computer.getResult()-TargetNumber)) {
			SolutionSteps();
		}
		else if (player.getResult() == TargetNumber) {
			SolutionSteps();
		}
		else 
		{
			System.out.println("Computer's solution steps:");
			while(!computerQueue2.isEmpty())
			{
				if(computerQueue2.peek()=="=")
				{
					System.out.print(" "+computerQueue2.dequeue() +" "+ computerQueue2.dequeue());
					System.out.println("");
				}
				else 
					System.out.print(" "+computerQueue2.dequeue());
			}
		}			
	}

	public void SolutionSteps() {
		//taking the players solution and processing conversion and evaluation.
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter player's solution: ");
		String solution = scan.next();
		player.setSolution(InfixToPostfix(solution));
		System.out.println(player.getSolution());
		int playerresult = evaluatePostfix(player.getSolution());
		//controlling the player input result and solution result to define it is correct or not.
		if (player.getResult() == playerresult) {
			player.setResult(playerresult);
			System.out.println(player.getResult());
			System.out.println("Correct...");
		}
		else {
			System.out.println("Result is not correct");
			player.setScore(player.getScore()-5);
			player.setResult(0);
			computer.setResult(0);
		}
	}

	public void ScoreControl() {
		//controlling result to increase score by the rules.
		if (Math.abs(player.getResult()-TargetNumber) <= Math.abs(computer.getResult()-TargetNumber)) {
			if (Math.abs(player.getResult()-TargetNumber) <= 10) {
				player.setScore(player.getScore() + (15-Math.abs(TargetNumber-player.getResult())));
			}
			else {
				player.setScore(player.getScore()+5);
			}
		}
		else if (Math.abs(player.getResult()-TargetNumber) > Math.abs(computer.getResult()-TargetNumber)) {
			if (Math.abs(computer.getResult()-TargetNumber) <= 10){
				computer.setScore(computer.getScore() + (15-Math.abs(TargetNumber-computer.getResult())));
			}
			else {
				computer.setScore(computer.getScore()+5);
			}
		}
		else {
			if (Math.abs(player.getResult()-TargetNumber) <= 10) {
				player.setScore(player.getScore() + (15-Math.abs(TargetNumber-player.getResult())));
				computer.setScore(computer.getScore() + (15-Math.abs(TargetNumber-computer.getResult())));
			}
			else {
				player.setScore(player.getScore()+5);
				computer.setScore(computer.getScore()+5);
			}
		}
	}

	public void PrintScore() {
		System.out.println("\n\nPlayer Score: " + player.getScore());
		System.out.println("\nComputer Score: " + computer.getScore());
		System.out.println("");
	}

	public int SignControl(char chr) {
		//precedence control for operators.
		switch (chr) 
		{ 
		case '+': 
		case '-': 
			return 1; 

		case '*': 
		case '/': 
			return 2; 

		case '^': 
			return 3; 
		} 
		return 0; 
	}

	public String InfixToPostfix(String exp) {
		System.out.println("");
		System.out.println("Infix to postfix conversion steps:");
		System.out.println("");
		int step=1;
		String result = new String(""); 
		for (int i = 0; i < exp.length(); i++) { 		
			while (exp.charAt(i) == ' ') {
				++i;
			}

			if (Character.isDigit(exp.charAt(i))) { 
				result += exp.charAt(i);
				if (i+1 >= exp.length() || !Character.isDigit(exp.charAt(i+1))) { 
					result += ' ';
				} 
			}

			else if (exp.charAt(i) == '(') 
				solutionstack.Push(exp.charAt(i)); 

			else if (exp.charAt(i) == ')') { 
				while (!solutionstack.isEmpty() && !solutionstack.Peek().equals('(')) {
					result += solutionstack.Pop(); 
					result += " ";
				}

				if (!solutionstack.isEmpty() && !solutionstack.Peek().equals('(')) 
					return "Invalid Expression";                
				else
					solutionstack.Pop(); 
			} 
			else { 
				while (!solutionstack.isEmpty() && SignControl(exp.charAt(i)) <= SignControl((char)solutionstack.Peek())) {
					result += solutionstack.Pop(); 
					result += " ";
				} 
				solutionstack.Push(exp.charAt(i)); 
			} 

			if ((i+1 >= exp.length() || !Character.isDigit(exp.charAt(i+1)))&&exp.charAt(i)!=')') {

				System.out.println("Step "+step+"  -  "+exp);
				System.out.println("");
				System.out.println(result+" ");
				System.out.println("");
				for(int j=0;j<5-solutionstack.Size();j++) {
					System.out.println("                        |   |");
				}
				while(!solutionstack.isEmpty()) {

					System.out.println("                        | "+solutionstack.Peek()+" |");
					solutionstacktemp2.Push(solutionstack.Pop());
				}
				while(!solutionstacktemp2.isEmpty()) {
					solutionstack.Push(solutionstacktemp2.Pop());
				}	
				step++;
				System.out.println("                        -----");
				System.out.println("");
			}	
		} 

		while (!solutionstack.isEmpty()) {
			System.out.println("Step "+step+"  -  "+exp);
			System.out.println("");
			step++;
			System.out.println(result+" ");
			result += solutionstack.Peek(); 
			result += " ";
			System.out.println("");
			for(int j=0;j<5-solutionstack.Size();j++) {
				System.out.println("                        |   |");
			}
			while(!solutionstack.isEmpty()) {
				System.out.println("                        | "+solutionstack.Peek()+" |");					
				solutionstacktemp2.Push(solutionstack.Pop());
			}
			System.out.println("                        -----");

			while(!solutionstacktemp2.isEmpty()) {
				solutionstack.Push(solutionstacktemp2.Pop());
			}
			solutionstacktemp.Push(solutionstack.Pop());
			System.out.println("");
		}
		
		System.out.println("Step "+step+"  -  "+exp);
		System.out.println("");
		System.out.println(result);
		System.out.println("");
		for(int i=0;i<5;i++) {
			System.out.println("                        |   |");
		}
		System.out.println("                        -----");
		System.out.println("");

		return result; 			
	} 

	public int evaluatePostfix(String exp) {
		int step=0;
		for(int i = 0; i < exp.length(); i++) { 

			char c = exp.charAt(i); 
			if(c == ' ') 
				continue; 

			else if(Character.isDigit(c)) { 
				int n = 0; 

				while(Character.isDigit(c)) { 
					n = n*10 + (int)(c-'0'); 
					i++; 
					c = exp.charAt(i); 
				} 
				i--; 
				solutionstack.Push(n); 
				step++;
			} 

			else { 
				int val1 = (int)solutionstack.Pop(); 
				int val2 = (int)solutionstack.Pop(); 

				switch(c) 
				{ 
				case '+': 
					solutionstack.Push(val2+val1); 
					break; 

				case '-': 
					solutionstack.Push(val2- val1); 
					break; 

				case '/': 
					solutionstack.Push(val2/val1); 
					break; 

				case '*': 
					solutionstack.Push(val2*val1); 
					break; 
				} 	
				step++;
			} 
			System.out.println("");
			System.out.println("Step "+step);
			System.out.println();
			System.out.println(exp);
			for(int j=0;j<5-solutionstack.Size();j++) {
				System.out.println("                        |   |");
			}

			while(!solutionstack.isEmpty()) {
				if((int)solutionstack.Peek()<10)
					System.out.println("                        | "+solutionstack.Peek()+" |");
				else if((int)solutionstack.Peek()<100)
					System.out.println("                        |"+solutionstack.Peek()+" |");
				else 
					System.out.println("                        |"+solutionstack.Peek()+"|");

				solutionstacktemp2.Push(solutionstack.Pop());
			}
			System.out.println("                        -----");
			while(!solutionstacktemp2.isEmpty()) {
				solutionstack.Push(solutionstacktemp2.Pop());
			}
		} 
		return (int)solutionstack.Pop();  
	} 

	public int ComputerAI(int[] randomnumbers) {
		int[] tempnumbers = new int[6];
		for (int k = 0; k < randomnumbers.length; k++) {
			tempnumbers[k] = randomnumbers[k];
		}
		Random random = new Random();
		int result = 0;
		int i=0;
		boolean flag = true;
		while(i<100) {
			i++;
			int operator = (int) (Math.random()*4);
			int numberone = random.nextInt(6);
			int numbertwo = random.nextInt(6);
			if (numberone != numbertwo && tempnumbers[numberone] != 0 && tempnumbers[numbertwo] != 0) {
				if (operator == 0) {
					result = tempnumbers[numberone] + tempnumbers[numbertwo];

					computerQueue.enqueue(tempnumbers[numberone]);
					computerQueue.enqueue("+");
					computerQueue.enqueue(tempnumbers[numbertwo]);
					computerQueue.enqueue("=");
					computerQueue.enqueue(result);

				}
				else if (operator == 1) {
					result = Math.abs(tempnumbers[numberone] - tempnumbers[numbertwo]);

					if(tempnumbers[numberone]>tempnumbers[numbertwo]) {
						computerQueue.enqueue(tempnumbers[numberone]);
						computerQueue.enqueue("-");
						computerQueue.enqueue(tempnumbers[numbertwo]);
						computerQueue.enqueue("=");
						computerQueue.enqueue(result);
					}
					else { 
						computerQueue.enqueue(tempnumbers[numbertwo]);
						computerQueue.enqueue("-");
						computerQueue.enqueue(tempnumbers[numberone]);
						computerQueue.enqueue("=");
						computerQueue.enqueue(result);
					}
				}
				else if (operator == 2) {
					result = tempnumbers[numberone] * tempnumbers[numbertwo];
					computerQueue.enqueue(tempnumbers[numberone]);
					computerQueue.enqueue("*");
					computerQueue.enqueue(tempnumbers[numbertwo]);
					computerQueue.enqueue("=");
					computerQueue.enqueue(result);
				}
				else if (operator == 3) {
					result = tempnumbers[numberone] / tempnumbers[numbertwo];
					computerQueue.enqueue(tempnumbers[numberone]);
					computerQueue.enqueue("/");
					computerQueue.enqueue(tempnumbers[numbertwo]);
					computerQueue.enqueue("=");
					computerQueue.enqueue(result);
				}
				tempnumbers[numberone] = result;
				tempnumbers[numbertwo] = 0;
			}			
		}
		if (Math.abs(computer.getBest()-TargetNumber) > Math.abs(result-TargetNumber)) {
			computer.setBest(result);

			while(!computerQueue2.isEmpty()){
				computerQueue2.dequeue();
			}				
			while(!computerQueue.isEmpty()) {
				computerQueue2.enqueue(computerQueue.dequeue());
			}				
		}
		else
			while(!computerQueue.isEmpty()) {
				computerQueue.dequeue();
			}
		return computer.getBest();
	}
}




