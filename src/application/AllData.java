
package application;

// This class to contain all data to suer it in table view
public class AllData <T extends MyInfo<T>> {

	// Attributes of AllData
	private T car;
	private DoubleNode current;

	// Constructor to make objects of AllData
	public AllData(T car, DoubleNode current) {
		super();
		this.car = car;
		this.current = current;
	}

	// This method to get martyr
	public T getInfo() {
		return car;
	}

	
	// This method to set new martyr
	public void setBrand(T car) {
		this.car = car;
	}

	public DoubleNode getCurrent() {
		return current;
	}

	public void setInfo(T car) {
		this.car = car;
	}

	public void setCurrent(DoubleNode current) {
		this.current = current;
	}

}
