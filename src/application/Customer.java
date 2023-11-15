package application;

public class Customer {

	private String name;	
	private String mobileNumber;
	
	
	
	public Customer(String name, String mobileNumber) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	public String getInfo() {
		return  name + ", " + mobileNumber;
	}
	
	
	
}
