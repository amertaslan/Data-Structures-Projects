import java.util.Random;

//i have deleted or haven't wrote unused methods of single linked list because of efficiency.
public class SingleLinkedList {
	private SingleLinkedListNode head;

	public SingleLinkedList() {
		head = null;
	}

	public void addFront(Object data, int x, int y, int direction) {

		//isCrashed algoritma mantıgı olarak dogru ancak addFront u uygun olarak ayarlamak gerekir.
		/*  if (direction == 1) {
            y++;
        } else if (direction == 2) {
            x--;
        } else if (direction == 3) {
            y--;
        } else if (direction == 0) {
            x++;
        }*/
		SingleLinkedListNode newSingleLinkedListNode = new SingleLinkedListNode(data, x, y);
		newSingleLinkedListNode.setLink(head);
		head = newSingleLinkedListNode;

	}

	public void addToEnd(Object data, int x, int y) {//Sona doğru ekleme
		SingleLinkedListNode newSingleLinkedListNode = new SingleLinkedListNode(data, x, y);
		if (head == null) {
			head = newSingleLinkedListNode;
		} else {
			SingleLinkedListNode temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			temp.setLink(newSingleLinkedListNode);
		}
	}

	public int size() {//boyutunu öðrenme methodu
		SingleLinkedListNode temp = head;
		int counter = 0;
		while (temp != null) {
			counter++;
			temp = temp.getLink();
		}
		return counter;
	}

	//x ve y leri alıp canvasa eklemen lazım

	public void update(char[][] canvas, Snake snake) {
		SingleLinkedListNode temp = head;
		while (temp != null) {
			canvas[temp.getX()][temp.getY()] = (char) temp.getData();
			temp = temp.getLink();
		}
	}


	public boolean isSnake(int x,int y){
		boolean flag=false;
		SingleLinkedListNode temp=head;
		for (int i = 0; i < size(); i++) {
			if (temp.getX()==x && temp.getY()==y){
				flag=true;
				break;
			}
			temp=temp.getLink();
		}
		return flag;
	}

	public boolean moveSnakeinSll(char[][] canvas, Snake snake, char[] letters) {
		SingleLinkedListNode temp = head;

		int x = snake.getX();//başı x y ye ekle
		int y = snake.getY();

		while (temp != null) {
			int xTemp = temp.getX();//şimdikini tut
			int yTemp = temp.getY();
			temp.setX(x);//öncekini şimdikine koy
			temp.setY(y);
			x = xTemp;//az önce tuttuğunu sonrakisine eklemek için x y ye yaz.
			y = yTemp;

			temp = temp.getLink();
		}
		canvas[x][y]=' ';
		boolean isAteSomething = dinner(canvas, snake,letters);
		if (!isAteSomething) {
			canvas[x][y] = ' ';//olmadı//sonrakine boşluk ekle, nasıl olsa daha bişi gelicekse harf yazılır. while dönmezse de
			//boşluk kalır işte ne güzel :)
		}
		snake.setLastx(x);//yani bunlar ilk 3ü hariç gerekli değil aslında buraya yazmasam da olur çünkü kullanmıyorum
		snake.setLasty(y);//ama belki ilerde lazım olur kullanırım dursun bakalım...
		return isAteSomething;
	}

	public boolean dinner(char[][] canvas, Snake snake, char[] letters) {
		if (!(canvas[snake.getX()][snake.getY()] == ' ' || canvas[snake.getX()][snake.getY()] == '#')) {//buraya 4 if ile x y ekle ayrı x y ler
			snake.addToSnakeFront(canvas[snake.getX()][snake.getY()], snake.getX(), snake.getY());
			addingRandomLetter(canvas,letters);
			return true;
		}
		return false;
	}


	public void addingRandomLetter(char[][] canvas, char[] letters) {
		Random rnd = new Random();
		int[] coordinates = selectingEmptyCoordinates(canvas);
		canvas[coordinates[0]][coordinates[1]] = letters[rnd.nextInt(4)];
	}
	private int[] selectingEmptyCoordinates(char[][] canvas) {
		int x, y;
		do {
			Random rnd = new Random();
			x = rnd.nextInt(23) + 1;
			y = rnd.nextInt(58) + 1;
		} while (canvas[x][y] != ' ');
		int[] retValues = new int[2];
		retValues[0] = x;
		retValues[1] = y;
		return retValues;
	}

	public boolean isCrashed() {//kesişiyor başa dönücez
		SingleLinkedListNode temp = head;
		while (temp.getLink().getLink() != null) {
			SingleLinkedListNode temp2 = temp.getLink().getLink();
			while (temp2 != null) {
				if (temp.getX() == temp2.getX() && temp.getY() == temp2.getY()) {
					return true;
				}
				temp2 = temp2.getLink();
			}
			temp = temp.getLink();
		}
		return false;
	}

	public String display() {
		String output = "";

		SingleLinkedListNode temp = head;

		while (temp != null) {
			output += temp.getData();
			temp = temp.getLink();
		}

		return output;
	}

	public boolean delete(int data) {
		SingleLinkedListNode SingleLinkedListNodeBeforeDelete = this.head;
		if (SingleLinkedListNodeBeforeDelete == null) { // List in empty
			return false;
		} else if ((int) SingleLinkedListNodeBeforeDelete.getData() == data) {
			this.head = this.head.getLink();
			return true;
		}
		while (true) {
			SingleLinkedListNode next = SingleLinkedListNodeBeforeDelete.getLink();
			if (next == null) { // No data found in list
				return false;
			} else if ((int) next.getData() == data) {
				break;
			}
			SingleLinkedListNodeBeforeDelete = next;
		}
		SingleLinkedListNode next = SingleLinkedListNodeBeforeDelete.getLink();
		SingleLinkedListNodeBeforeDelete.setLink(next.getLink());
		next.setLink(null);
		return true;
	}//deletes just one of them if exist.

	public boolean search(Object dataToSearch) {
		if (head == null) {
			return false;
		} else {
			SingleLinkedListNode temp = head;

			boolean retVal = false;
			while (temp != null) {
				if ((int) temp.getData() == (int) dataToSearch) {
					retVal = true;
					break;
				}

				temp = temp.getLink();
			}

			return retVal;
		}
	}//searches and returns true if it exist

	public int howMany(Object dataToSearch) {
		if (head == null) {
			return 0;
		} else {
			SingleLinkedListNode temp = head;

			int retVal = 0;
			while (temp != null) {
				if ((int) temp.getData() == (int) dataToSearch) {
					retVal++;
				}

				temp = temp.getLink();
			}

			return retVal;
		}
	}//searches and return an integer value of that how many exist from this integer

	/*  Function to check if list is  empty   */
	public boolean isEmpty() {
		return head == null;
	}

	public void reverse() {
		SingleLinkedListNode prev = null; 
		SingleLinkedListNode current = head; 
		SingleLinkedListNode next = null; 
		while (current != null) { 
			next = current.getLink();
			current.setLink(prev);; 
			prev = current; 
			current = next;
		}
		head = prev;
	}

	public String findCodon() {
		SingleLinkedListNode temp = head;
		String codon = "";
		int counter = 0;
		while (counter < 3) {
			codon += temp.getData();
			counter++;
			temp = temp.getLink();
		}
		return codon;
	}
}