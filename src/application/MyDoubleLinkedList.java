package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//This class to save list of location data
public class MyDoubleLinkedList {

	// Pointer to first double node.
	private DoubleNode first;
	// Pointer to last double node.
	private DoubleNode last;
	// size of list
	private int count;

	public DoubleNode getFirstDoubleNode() {
		return first;
	}

	// This method to add location in the first place in this list.
	public void addFirst(String brand) {
		if (brand == null || brand.isEmpty())
			return;
		if (count == 0) {
			first = last = new DoubleNode(brand);
		} else {
			DoubleNode tmp = new DoubleNode(brand);
			tmp.setNext(first);
			first.setPre(tmp);
			first = tmp;
		}
		count++;
	}

	// This method to return if their is no location
	public boolean isEmpty() {
		return count == 0;
	}

	// This method to add location in the last place in this list.
	public void addLast(String brand) {
		if (brand == null || brand.isEmpty())
			return;
		if (count == 0) {
			first = last = new DoubleNode(brand);
		} else {
			DoubleNode tmp = new DoubleNode(brand);
			last.setNext(tmp);
			tmp.setPre(last);
			last = tmp;
		}
		count++;
	}

	/*
	 * This method add the location in the place dependent on location name in this
	 * list and martyr in the place dependent on date of death in link list of the
	 * same location.
	 */
	public void add(Car car, String brand) {
		if (brand == null || brand.isEmpty() || car == null)
			return;

		if (isEmpty()) {
			addFirst(brand);
			first.getStart().add(car);
			return;
		} else {
			int compare = brand.compareToIgnoreCase(first.getBrand());
			if (compare < 0) {

				addFirst(brand);
				first.getStart().add(car);
				return;
			} else if (compare == 0) {
				first.getStart().add(car);
				return;
			}

			compare = brand.compareToIgnoreCase(last.getBrand());
			if (compare > 0) {
				addLast(brand);
				last.getStart().add(car);
				return;
			} else if (compare == 0) {
				last.getStart().add(car);
				return;
			}

			DoubleNode current = first;
			while (current != null) {
				compare = brand.compareToIgnoreCase(current.getBrand());
				if (compare > 0 && brand.compareToIgnoreCase(current.getNext().getBrand()) < 0) {
					DoubleNode node = new DoubleNode(brand);
					node.getStart().add(car);
					node.setNext(current.getNext());
					node.setPre(current);
					current.getNext().setPre(node);
					current.setNext(node);
					count++;
					return;
				} else if (compare == 0) {
					current.getStart().add(car);
					return;
				}
				current = current.getNext();
			}
		}
	}

	// This method to add location in the place dependent on location in this list.
	public boolean add(String brand) {
		if (brand == null || brand.isEmpty())
			return false;
		if (count == 0) {
			addFirst(brand);
			return true;
		} else {
			int compare = brand.compareToIgnoreCase(first.getBrand());
			if (compare < 0) {

				addFirst(brand);
				return true;
			} else if (compare == 0) {

				return false;
			}

			compare = brand.compareToIgnoreCase(last.getBrand());
			if (compare > 0) {
				addLast(brand);
				return true;
			} else if (compare == 0) {
				return false;
			}

			DoubleNode current = first;
			while (current != null) {
				compare = brand.compareToIgnoreCase(current.getBrand());
				if (compare > 0 && brand.compareToIgnoreCase(current.getNext().getBrand()) < 0) {
					DoubleNode node = new DoubleNode(brand);

					node.setNext(current.getNext());
					node.setPre(current);
					current.getNext().setPre(node);
					current.setNext(node);
					count++;
					return true;
				} else if (compare == 0) {
					return false;
				}
				current = current.getNext();
			}
		}

		return false;
	}

	/*
	 * This method to read martyr data from input file with format
	 * (name,age,location,date(month/day/year),gender(M,F)) then add the location in
	 * the place dependent on location name in this list and martyr in the place
	 * dependent on date of death in link list of the same location.
	 */
	public int readCar(File f) throws Exception {
		int errorCount = 0;
		try {
			Scanner scanner = new Scanner(f);

			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(",");
				if (line.length != 5) {
					errorCount++;
					continue;
				}
				Car car;
				try {
					car = new Car(line[1].trim(), Short.parseShort(line[2].trim()), line[3].trim(), line[4].trim());
					add(car, line[0].trim()
							);
				} catch (Exception e) {
					errorCount++;
				}

			}
			scanner.close();
		} catch (Exception e) {
			throw e;
		}

		return errorCount;
	}

	// This method to add all location data in the grid pane
	public void addBrandToGP(GridPane gp, Stage stage) {

		DoubleNode current = first;
		gp.add(GeneralPanes.brandPane(null, stage, this), 1, 0);
		for (int x = 2; x < count + 2; x++) {
			gp.add(GeneralPanes.brandPane(current, stage, this), x % 7, x / 7);
			current = current.getNext();
		}

	}

	// This method to add all location data in the grid pane
	public void addBrandToGP(GridPane gp, Stage stage, Customer customer) {

		DoubleNode current = first;
		gp.add(GeneralPanes.brandPane(null, stage, this, customer), 0, 0);
		for (int x = 1; x < count + 1; x++) {
			gp.add(GeneralPanes.brandPane(current, stage, this, customer), x % 7, x / 7);
			current = current.getNext();
		}

	}

	// This method to add search location data in the grid pane
	public void addBrandToGP(GridPane gp, String search, Stage stage) {
		DoubleNode current = first;
		boolean isEmpty = true;

		for (int x = 2, pos = 2; x < count + 2; x++) {
			if (current.getBrand().toLowerCase().startsWith(search.toLowerCase())) {
				gp.add(GeneralPanes.brandPane(current, stage, this), pos % 7, pos / 7);
				pos++;
				if (isEmpty)
					isEmpty = false;
			}
			current = current.getNext();
		}
		if (isEmpty && !"All".toLowerCase().startsWith(search.toLowerCase())) {
			Label emptyLabel = new Label("Empty List");
			gp.setMargin(emptyLabel, new Insets(45));
			gp.add(emptyLabel, 1, 0);
		} else {
			gp.add(GeneralPanes.brandPane(null, stage, this), 1, 0);
		}
	}

	// This method to add search location data in the grid pane
	public void addBrandToGP(GridPane gp, String search, Stage stage,Customer customer) {
			DoubleNode current = first;
			boolean isEmpty = true;

			for (int x = 1, pos = 1; x < count + 1; x++) {
				if (current.getBrand().toLowerCase().startsWith(search.toLowerCase())) {
					gp.add(GeneralPanes.brandPane(current, stage, this,customer), pos % 7, pos / 7);
					pos++;
					if (isEmpty)
						isEmpty = false;
				}
				current = current.getNext();
			}
			if (isEmpty && !"All".toLowerCase().startsWith(search.toLowerCase())) {
				Label emptyLabel = new Label("Empty List");
				gp.setMargin(emptyLabel, new Insets(45));
				gp.add(emptyLabel, 0, 0);
			} else {
				gp.add(GeneralPanes.brandPane(null, stage, this,customer),0, 0);
			}
		}

	// This method to print all location and martyr data to the input file
	public boolean printListToFile(File f) throws Exception {
		try {
			PrintWriter pw = new PrintWriter(f);
			DoubleNode current = first;
			while (current != null) {
				current.getStart().printListToFile(pw, current.getBrand());
				current = current.getNext();
			}
			for (int i = 0; i < 100; i++) {
				first.getStart().printListToFile(pw, "" + i);
			}
			pw.close();
			return true;
		} catch (FileNotFoundException e) {
			throw e;
		}

	}

	// This method to remove the input node
	public boolean removeDoubleNode(DoubleNode current) {
		if (current == null) {
			return false;
		}
		if (current.getPre() == null) {
			return removeFirst();
		} else if (current.getNext() == null) {
			return removeLast();
		} else {
			DoubleNode pre = current.getPre();
			DoubleNode next = current.getNext();
			pre.setNext(next);
			next.setPre(pre);
			count--;
			return true;
		}
	}

	// This method to remove the searched node by location and martyr
	public boolean removeNode(String brand, Car car) {
		DoubleNode current = first;
		while (current != null) {
			if (current.getBrand().equals(brand)) {
				return current.getStart().remove(car);
			}
			current = current.getNext();
		}
		return false;
	}

	// This method to get search node by location
	public DoubleNode getDoubleNode(String brand) {
		DoubleNode current = first;
		while (current != null) {
			if (current.getBrand().equals(brand))
				return current;
			current = current.getNext();
		}
		return current;
	}

	// This method to remove first location in this list.
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				last = first = null;
			} else {
				first = first.getNext();
				first.setPre(null);
			}
			count--;
			return true;
		}

	}

	// This method to remove last location in this list.
	public boolean removeLast() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				last = first = null;
			} else {
				last = last.getPre();
				last.setNext(null);

			}
			count--;
			return true;
		}
	}

	// This method to update location name and place of node in this list.
	public boolean changeName(String brand, DoubleNode node) {
		if (brand == null || brand.isEmpty() || node == null)
			return false;

		int compare = brand.compareToIgnoreCase(node.getBrand());

		if (compare > 0) {
			compare = brand.compareToIgnoreCase(last.getBrand());
			if (compare > 0) {
				if (node.getNext() == null) {
					// no need to change the place of node
					node.setBrand(brand);
					return true;
				} else if (node.getPre() == null) {
					DoubleNode current = node;

					// put the first node to the second node
					first = node.getNext();
					first.setPre(null);

					// put node in the last
					last.setNext(current);
					current.setNext(null);
					current.setPre(last);
					last = current;
					node.setBrand(brand);
					return true;
				} else {
					DoubleNode current = node;

					// connect the pre node to the next node
					node.getPre().setNext(node.getNext());
					node.getNext().setPre(node.getPre());

					// put node in the last
					last.setNext(current);
					current.setNext(null);
					current.setPre(last);
					last = current;

					node.setBrand(brand);
					return true;
				}

			} else if (compare == 0) {
				return false;
			}

			if (brand.compareToIgnoreCase(node.getBrand()) > 0
					&& brand.compareToIgnoreCase(node.getNext().getBrand()) < 0) {
				node.setBrand(brand);
				return true;
			}
			DoubleNode current = node;
			while (current != null) {
				compare = brand.compareToIgnoreCase(current.getBrand());
				if (compare > 0 && brand.compareToIgnoreCase(current.getNext().getBrand()) < 0) {

					DoubleNode tmp = node;
					// connect the pre node to the next node
					if (node.getPre() != null) {
						node.getPre().setNext(node.getNext());
					}

					if (tmp.getNext() != null) {
						tmp.getNext().setPre(tmp.getPre());
					}

					tmp.setNext(current.getNext());
					tmp.setPre(current);
					current.getNext().setPre(tmp);
					current.setNext(tmp);

					tmp.setBrand(brand);
					return true;
				} else if (compare == 0) {
					return false;
				}

				current = current.getNext();

			}

		} else if (compare < 0) {
			compare = brand.compareToIgnoreCase(first.getBrand());
			if (compare < 0) {
				if (node.getPre() == null) {
					// no need to change the place of node
					node.setBrand(brand);
					return true;
				} else if (node.getNext() == null) {
					DoubleNode current = node;

					// put the Last node to the pre last node
					last = last.getPre();
					last.setNext(null);

					// put node in the first
					first.setPre(current);
					current.setPre(null);
					current.setNext(first);
					first = current;
					node.setBrand(brand);
					return true;
				} else {
					DoubleNode current = node;

					// connect the pre node to the next node
					node.getPre().setNext(node.getNext());
					node.getNext().setPre(node.getPre());

					// put node in the first
					first.setPre(current);
					current.setPre(null);
					current.setNext(first);
					first = current;
					node.setBrand(brand);

					return true;
				}

			} else if (compare == 0) {
				return false;
			}

			if (brand.compareToIgnoreCase(node.getBrand()) < 0
					&& brand.compareToIgnoreCase(node.getPre().getBrand()) > 0) {
				node.setBrand(brand);
				return true;
			}
			DoubleNode current = node;
			while (current != null) {
				compare = brand.compareToIgnoreCase(current.getBrand());
				if (compare < 0 && brand.compareToIgnoreCase(current.getPre().getBrand()) > 0) {

					DoubleNode tmp = node;
					// connect the pre node to the next node
					if (node.getPre() != null) {
						node.getPre().setNext(node.getNext());
					}

					if (node.getNext() != null) {
						node.getNext().setPre(node.getPre());
					}

					tmp.setPre(current.getPre());
					tmp.setNext(current);
					current.getPre().setNext(tmp);
					current.setPre(tmp);

					node.setBrand(brand);
					return true;
				} else if (compare == 0) {
					return false;
				}

				current = current.getPre();

			}
		}

		return false;

	}


	// This method to add all martyr in all location in this list to the table view.
	public void addAllDataToTableView(TableView<AllData<Car>> tv) {
		DoubleNode current = first;
		current = first;
		while (current != null) {
			current.getStart().addAllDataToTableView(tv, current);
			current = current.getNext();
		}

	}

	// This method to add martyr searched with name in all location in this list to
	// the table view.
	public void addAllDataToTableView(TableView<AllData<Car>> tv, String search, Car filter) {
		DoubleNode current = first;
		current = first;
		while (current != null) {
			if (current.getBrand().trim().toLowerCase().contains(search.trim().toLowerCase()))
				current.getStart().addAllDataToTableView(tv, current, filter);
			current = current.getNext();
		}

	}
}
