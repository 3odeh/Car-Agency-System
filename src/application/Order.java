package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements MyInfo<Order>{
	private Customer customer;
	private Car car;
	private Date date;
	private boolean isFinished;
	private String brand;


	public Order(Customer customer, Car car,String brand, Date date, boolean isFinished) {
		super();
		this.customer = customer;
		this.car = car;
		this.date = date;
		this.isFinished = isFinished;
		this.brand = brand;
	}
	
	public Order(Customer customer, Car car,String brand, String date, boolean isFinished) throws ParseException {
		super();
		this.customer = customer;
		this.car = car;
		this.isFinished = isFinished;
		this.brand = brand;
		setDate(date);
	}
	
	

	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public Date getDate() {
		return date;
	}

	public String getSimpleDate() {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public boolean isFinished() {
		return isFinished;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String date) throws ParseException {
		try {
			this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			throw e;
		}
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String getInfo(String brand) {
		return customer.getInfo() + ", " + car.getInfo(brand) + ", " + date + ", "
				+ ((isFinished) ? "Finished" : "InProcess");
	}
	
	
	public String getInfo() {
		return customer.getInfo() + ", " + car.getInfo(brand) + ", " + getSimpleDate() + ", "
				+ ((isFinished) ? "Finished" : "InProcess");
	}

	@Override
	public int compareTo(Order o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean filter(Order info) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void incrementCountRepeat() {
		// TODO Auto-generated method stub
		car.incrementCountRepeat();
	}



	@Override
	public void decrementCountRepeat() {
		// TODO Auto-generated method stub
		car.decrementCountRepeat();
	}
	
	@Override
	public int getCountRepeat() {
		// TODO Auto-generated method stub
		return car.getCountRepeat();
	}

}
