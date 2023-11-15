package application;

public class Car implements MyInfo<Car> {

	private String model;
	private short year;
	private String color;
	private String price;
	private int repeat = 1;

	public Car(String model, short year, String color, String price) {
		super();
		this.model = model;
		this.year = year;
		this.color = color;
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public short getYear() {
		return year;
	}

	public String getColor() {
		return color;
	}

	public String getPrice() {
		return price;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getInfo(String brand) {
		return brand + ", " + model + ", " + year + ", " + color + ", " + price;
	}

	@Override
	public int compareTo(Car car) {
		if (car == null)
			return 0;
		return this.getYear() - car.getYear();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj != null && obj instanceof Car) {
			Car car = (Car) obj;
			boolean b = this.model.trim().equalsIgnoreCase(car.getModel().trim())
					&& this.year == car.getYear()
					&& this.color.trim().equalsIgnoreCase(car.getColor().trim())
					&& this.price.trim().equalsIgnoreCase(car.getPrice().trim());
			return b;
		}

		return false;
	}

	@Override
	public boolean filter(Car car) {

		if (car == null)
			return true;

		boolean filterModel = car.model == null || car.model.isEmpty()
				|| (this.model.trim().toLowerCase().contains(car.model.trim().toLowerCase()));
		boolean filterColor = car.color == null || car.color.isEmpty()
				|| (this.color.trim().toLowerCase().contains(car.color.trim().toLowerCase()));
		boolean filterPrice = car.price == null || car.price.isEmpty()
				|| (this.price.trim().toLowerCase().contains(car.price.trim().toLowerCase()));
		boolean filterYear = car.year == 0 || (this.year == car.year);
		return filterColor && filterModel && filterPrice && filterYear;
	}

	public int getRepeat() {
		return repeat;
	}

	@Override
	public void incrementCountRepeat() {
		repeat++;

	}

	@Override
	public void decrementCountRepeat() {
		repeat--;

	}

	@Override
	public int getCountRepeat() {
		// TODO Auto-generated method stub
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	

}
