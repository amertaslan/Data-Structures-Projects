
public class Stack {
	private Object[] stack = null;
	private int top;
	
	public Stack(int capacity) {
		this.stack = new Object[capacity];
		this.top = 0;
	}
	
	public void Push(Object object) {
		if (top == stack.length) {
			System.out.println("Stack overflow!!!");
		}
		else {
			stack[top] = object;
			top++;
		}
	}
	
	public Object Pop() {
		Object tempobject = ' ';
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return tempobject;
		}
		else {
			tempobject = stack[top-1];
			top--;
			return tempobject;
		}
	}
	
	public Object Peek() {
		if (isEmpty()) {
			System.out.println("Stack is empty!!!");
			return ' ';
		}
		else {
			return stack[top-1];
		}
	}
	
	public boolean isEmpty() {
		if (top == 0) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (top == 0) {
			return false;
		} 
		else {
			return true;
		}
	}
	
	public int Size() {
		return top;
	}
	
	public int Length() {
		return stack.length;
	}
}
