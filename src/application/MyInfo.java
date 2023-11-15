package application;

public interface MyInfo<T> extends Comparable<T>{

	
	public String getInfo (String info);
	public boolean filter (T info);
	public void incrementCountRepeat();
	public void decrementCountRepeat();
	public int getCountRepeat();
}
