package application;

//This class for save data of location with linked list of martyr and the next node
public class DoubleNode {

	// Attributes of double node
	private String brand;
	private MyLinkedList<Car> cars;
	private DoubleNode pre, next;

	// Constructor to make objects of double node with location data and initialize
	// the linked list
	public DoubleNode(String brand) {
		super();
		this.brand = brand;
		cars = new MyLinkedList();
	}

	// This method to get location
	public String getBrand() {
		return brand;
	}

	// This method to get linked list
	public MyLinkedList getStart() {
		return cars;
	}

	// This method to get previous double node
	public DoubleNode getPre() {
		return pre;
	}

	// This method to get next double node
	public DoubleNode getNext() {
		return next;
	}

	// This method to set update location
	public void setBrand(String brand) {
		this.brand = brand;
	}

	// This method to set set new linked list
	public void setStart(MyLinkedList car) {
		this.cars = car;
	}

	// This method to set new previous double node
	public void setPre(DoubleNode pre) {
		this.pre = pre;
	}

	// This method to set new next double node
	public void setNext(DoubleNode next) {
		this.next = next;
	}

}
