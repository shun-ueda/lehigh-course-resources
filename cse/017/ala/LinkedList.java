import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> {
	// Data members
	private Node head, tail;
	int size;

	// Inner class Node
	private class Node {
		E value;
		Node next;

		Node(E initialValue) {
			value = initialValue;
			next = null;
		}
	}

	// Constructor
	public LinkedList() {// O(1)
		head = tail = null;
		size = 0;
	}

	// Adding an item to the list
	public boolean addFirst(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) { // adding the first node
			head = tail = newNode;
		} else {
			newNode.next = head;
			head = newNode;
		}
		size++;
		return true;
	}

	public boolean addLast(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	public boolean add(E item) { // O(1)
		return addFirst(item);
	}
	// Retrieving an item from the list
	public E getFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return head.value;
	}

	public E getLast() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return tail.value;
	}
	// Removing an item from the list
	public boolean removeFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		head = head.next;
		if (head == null)
			tail = null;
		size--;
		return true;
	}

	public boolean removeLast() { // O(n)
		if (head == null)
			throw new NoSuchElementException();
		if (size == 1)
			return removeFirst();
		Node current = head;
		Node previous = null;
		while (current.next != null) {
			previous = current;
			current = current.next;
		}
		previous.next = null;
		tail = previous;
		size--;
		return true;
	}
	
	// toString() method
	public String toString() { // O(n)
		String output = "[";
		Node node = head;
		while (node != null) {
			output += node.value + " ";
			node = node.next;
		}
		output += "]";
		return output;
	}

	// clear, check if empty, and size of the list
	public void clear() { // O(1)
		head = tail = null;
		size = 0;
	}

	public boolean isEmpty() { // O(1)
		return (size == 0);
	}

	public int size() { // O(1)
		return size;
	}
	// Generating an iterator for the list
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements Iterator<E> {
		private Node current = head;

		public boolean hasNext() { // O(1)
			return (current != null);
		}

		public E next() { // O(1)
			if (current == null)
				throw new NoSuchElementException();
			E value = current.value;
			current = current.next;
			return value;
		}
	}
	public int contains(E item) {
		Iterator<E> iter = iterator();
		int iterations = 0;
		while(iter.hasNext()) {
			iterations++;
			if(item.equals(iter.next())) {
				return iterations;
			}
		}
		return iterations;
	}
	
	public int remove(E item) {
		Node current = head;
		Node previous = null;
		int iterations=0;
		while(current!= null && !item.equals(current.value)) {
			previous = current;
			current = current.next;
			iterations++;
		}
		if(current != null) {
			if(previous == null)
				removeFirst();
			else {
				previous.next = current.next;
				size--;
			}
		}
		return iterations;
	}
	
	public int add(int index, E item) {
		if(index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException();	
		}
		if(index == 0) {
			addFirst(item);
			return 0;
		}
		if(index == size) {
			addLast(item);
			return 0;
		}
		int i=0;
		int iterations=0;
		Node current = head;
		Node previous = null;
		while(current!=null && i<index) {
			previous = current;
			current = current.next;
			i++;
			iterations++;
		}
		Node newNode = new Node(item);
		previous.next = newNode;
		newNode.next = current;
		size++;
		return iterations;
	}
}
