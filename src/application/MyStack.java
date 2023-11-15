package application;


public class MyStack <T extends MyInfo<T>>{

	private MyLinkedList<T> linkedList;
	private Object[] array;
	private int top;

	public MyStack() {
		linkedList = new MyLinkedList<>();
	}

	public MyStack(int size) {
		array = new Object[size];
	}

	public boolean isEmpty() {
		if (linkedList != null) {
			return linkedList.isEmpty();
		} else {
			return top == 0;
		}
	}

	public void push(T o) {

		if (linkedList != null) {
			linkedList.addFirst(o);
		} else {
			if (!isFull()) {
				array[top] = o;
				top++;
			} else {
				System.out.println("stack is full");
			}
		}
	}

	public T pop() {
		if (!isEmpty()) {
			if (linkedList != null) {
				T tmp = linkedList.getFirst();
				linkedList.removeFirst();
				return tmp;
			} else {
				top--;
				return (T) array[top];
			}
		} else {
			System.out.println("stack is empty");
			return null;
		}
	}

	public Object top() {
		if (!isEmpty()) {
			if (linkedList != null) {
				return linkedList.getLast();
			}else
				return array[top-1];
		}else {
			System.out.println("stack is empty");
			return null;
		}
	}

	

	public boolean isFull() {
		if (linkedList != null)
			return false;
		return top == array.length;

	}

}