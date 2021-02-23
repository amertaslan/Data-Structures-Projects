
public class DoubleLinkedListNode {
private Object data;
private DoubleLinkedListNode prev;
private DoubleLinkedListNode next;
private String name;
public DoubleLinkedListNode(String Name,Object dataToAdd) {
	data=dataToAdd;
	name=Name;
	prev=null;
	next=null;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
public DoubleLinkedListNode getPrev() {
	return prev;
}
public void setPrev(DoubleLinkedListNode prev) {
	this.prev = prev;
}
public DoubleLinkedListNode getNext() {
	return next;
}
public void setNext(DoubleLinkedListNode next) {
	this.next = next;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}