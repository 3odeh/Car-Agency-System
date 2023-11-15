package application;

import java.io.PrintWriter;
import java.util.LinkedList;

import javafx.scene.control.TableView;

// This class to save list of martyr data
public class MyLinkedList<T extends MyInfo<T>> {

	// Pointer to first node.
	private Node<T> first;
	// Pointer to last node.
	private Node<T> last;

	// This method to return if their is no martyr
	public boolean isEmpty() {
		return (first == null);
	}

	// This method to returns the first martyr in this list
	public T getFirst() {
		if (first != null)
			return first.getInfo();

		else
			return null;
	}

	// This method to returns the last martyr in this list.
	public T getLast() {
		if (last != null)
			return last.getInfo();

		else
			return null;
	}

	// This method to add martyr in the first place in this list.
	public void addFirst(T info) {

		if (first == null) {
			first = last = new Node<T>(info);

		} else {
			Node<T> tmp = new Node<T>(info);
			tmp.setNext(first);
			first = tmp;
		}

	}

	// This method to add martyr in the last place in this list.
	public void addLast(T info) {
		if (first == null) {
			first = last = new Node<T>(info);

		} else {
			Node<T> newN = new Node<T>(info);
			last.setNext(newN);
			last = last.getNext();

		}

	}

	// This method to add martyr in the place dependent on death date of martyr in
	// this list.
	public int add(T car) {
		if (first == null) {
			addFirst(car);
			return 0;
		} else {
			if (car.equals(first.getInfo())) {
				first.getInfo().incrementCountRepeat();
				return 0;
			}
			if (car.equals(last.getInfo())) {
				last.getInfo().incrementCountRepeat();
				return -2;
			}
			int compiar = car.compareTo(first.getInfo());
			if (compiar < 0) {
				addFirst(car);
				return 0;
			}
			compiar = car.compareTo(last.getInfo());
			if (compiar > 0) {
				addLast(car);
				return -1;
			}
			
			Node<T> current = first;
			int place = 1;
			boolean equality = true;
			Node<T> node = new Node<T>(car);
			while (current != null) {
				compiar = node.getInfo().compareTo(current.getInfo());
				if (equality && compiar == 0) {
					int tmp = 0;
					Node<T> tmpn = current;
					while (tmpn.getNext() != null && tmp == 0) {
						if (car.equals(tmpn.getInfo())) {
							tmpn.getInfo().incrementCountRepeat();
							return -2;
						}
						tmpn = tmpn.getNext();
						tmp = car.compareTo(tmpn.getInfo());
					}
					equality = false;
				}

				if (compiar >= 0 && current.getNext() != null && car.compareTo(current.getNext().getInfo()) < 0) {
					node.setNext(current.getNext());
					current.setNext(node);
					return place;
				} 
				place++;
				current = current.getNext();
			}
			
			compiar = car.compareTo(last.getInfo());
			if (compiar >= 0) {
				addLast(car);
				return -1;
			}
		}
		
		System.out.println("Does not added");
		return -3;
	}

	// This method to remove first martyr in this list.
	public boolean removeFirst() {

		if (first == null) {
			return false;

		} else {
			if (first == last) {
				last = first = null;
			}
			else {
				first = first.getNext();
			}
			return true;
		}

	}

	// This method to remove last martyr in this list.
	public boolean removeLast() {

		if (first == null) {
			return false;

		} else {
			if (last == first) {
				last = first = null;
			}
			else {
				Node<T> current = first;
				while (current.getNext().getNext() != null)
					current = current.getNext();

				last = current;
				last.setNext(null);
			}

			return true;
		}

	}

	// This method to remove input martyr in this list.
	public boolean remove(T car) {
		if (first == null) {
			return false;

		} else {
			if (first.getInfo().equals(car)) {
				return removeFirst();
			}
			if (last.getInfo().equals(car)) {
				return removeLast();
			}

			else {
				Node<T> previous = first;
				Node<T> current = first.getNext();
				while (current != null && !current.getInfo().equals(car)) {
					previous = current;
					current = current.getNext();
				}
				if (current != null) {
					previous.setNext(current.getNext());
					return true;
				} else
					return false;

			}
		}

	}

	// This method to search martyr in this list.
	public boolean search(T car) {

		Node<T> current = first;

		while (current != null) {
			if (current.getInfo().equals(car)) {
				return true;
			}
			current = first.getNext();
		}
		return false;

	}

	// This method to print all martyr in this list.
	public void printList() {

		try {
			Node<T> current = first;
			while (current != null) {
				System.out.println(current.getInfo().getInfo("brand") + ":" + current.getInfo().getCountRepeat());
				current = current.getNext();
			}
		} catch (Exception e) {
		}
	}

	// This method to print all martyr in this list on input file.
	public void printListToFile(PrintWriter pw, String brand) throws Exception {
		try {
			Node<T> current = first;
			while (current != null) {
				
				pw.println(current.getInfo().getInfo(brand));
				current = current.getNext();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// This method to add all martyr in this list to the table view.
	public void addDataToTableView(TableView<T> tv) {
		Node<T> current = first;
		while (current != null) {
			tv.getItems().add(current.getInfo());
			current = current.getNext();
		}
	}

	// This method to add martyr searched with name in this list to the table view.
	public void addDataToTableView(TableView<T> tv, T filter) {

		printList();
		Node<T> current = first;
		while (current != null) {
			if (current.getInfo().filter(filter))
				tv.getItems().add(current.getInfo());
			current = current.getNext();
		}
	}

	// This method to add martyr with location in this list to the table view.
	public void addAllDataToTableView(TableView<AllData<T>> tv, DoubleNode brand) {

		Node<T> current = first;
		while (current != null) {
			tv.getItems().add(new AllData<T>(current.getInfo(), brand));
			current = current.getNext();
		}
	}

	// This method to add location and martyr searched with name in this list to the
	// table view.
	public void addAllDataToTableView(TableView<AllData<T>> tv, DoubleNode brand, T filter) {

		Node<T> current = first;
		while (current != null) {
			if (current.getInfo().filter(filter))
				tv.getItems().add(new AllData(current.getInfo(), brand));
			current = current.getNext();
		}
	}

	// This method to update the place of node
	public void notifyChangeDate(T car) {
		remove(car);
		add(car);
	}

	// This method return the searched node by martyr
	public Node<T> getNode(T car) {
		Node<T> current = first;
		while (current != null) {
			if (current.getInfo().equals(car))
				return current;
			current = current.getNext();
		}
		return current;
	}

	// This method return the first node
	public Node<T> getFirstNode() {
		return first;
	}

}