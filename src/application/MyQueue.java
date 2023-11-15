package application;

import java.io.File;
import java.util.Scanner;

public class MyQueue <T extends MyInfo<T>>{

	private int size;

	private MyLinkedList<T> linkedList;
	private Object[] array;
	private int font;
	private int rear;

	public MyQueue() {
		linkedList = new MyLinkedList<>();
	}

	public MyQueue(int size) {
		array = new Object[size + 2];
		rear = size + 1;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public T dequeue() {
		if (isEmpty())
			return null;
		size--;
		if (linkedList != null) {
			T o = linkedList.getFirst();
			linkedList.removeFirst();
			return o;
		} else {
			rear = getNext(rear);
			return (T) array[rear];
		}
	}

	public void enqueue(T o) {
		if (!isFull()) {
			size++;
			if (linkedList != null) {
				linkedList.addLast(o);
			} else {
				array[font] = o;
				font = getNext(font);
			}
		} else
			System.out.println("The queue is full");
	}


	public void printQueue() {
		System.out.println(size);

		for (int i = 0; i < size; i++) {
			T o = dequeue();
			System.out.println(o.getInfo(""));
			enqueue(o);
		}
	}
	
	public T getFont() {
		if (isEmpty())
			return null;
		if (linkedList != null) {
			T o = linkedList.getFirst();
			return o;
		} else {
			return (T) array[getNext(rear)];
		}
	}

	private int getNext(int c) {
		if (c == (array.length - 1))
			return 0;
		return c + 1;
	}

	public boolean isFull() {
		if (linkedList != null)
			return false;
		return (rear == getNext(font));
	}


}