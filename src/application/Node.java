package application;


// This class for save data of martyr and the next node
public class Node <T extends MyInfo<T>>{


	
	// Attributes of node
	private T info;
	private Node <T> next;

	// Constructor to make objects of node with martyr data
	public Node(T info) {
		this.info = info;
	}

	// This method to get martyr
	public T getInfo() {
		return info;
	}

	// This method to get next node
	public Node<T> getNext() {
		return next;
	}

	// This method to set new martyr
	public void setInfo(T info) {
		this.info = info;
	}

	// This method to set new next node
	public void setNext(Node<T> next) {
		this.next = next;
	}

}
