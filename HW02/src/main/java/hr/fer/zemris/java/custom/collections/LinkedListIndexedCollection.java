package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * {@code LinkedListIndexedCollection} represents a collection implemented as a
 * linked list and provides methods for work with the collection. Collection
 * does not permit adding {@code null}. {@code LinkedListIndexedCollection} has
 * pointers to the first and the last node of the collection.
 * 
 * @author Filip Karacic
 *
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * Number of elements currently in the collection.
	 */
	private int size;
	
	/**
	 * Pointer to the first element of the collection.
	 */
	private ListNode first;
	
	/**
	 * Pointer to the last element of the collection.
	 */
	private ListNode last;

	/**
	 * Initialize a newly created {@code LinkedListIndexedCollection} object that
	 * represents an empty collection.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Initialize a newly created {@code LinkedListIndexedCollection} object that
	 * represents an empty collection and adds {@code other} collection's elements
	 * to this collection.
	 * 
	 * @param other collection with elements to be added to this collection
	 * 
	 * @throws NullPointerException if {@code other} is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		Objects.requireNonNull(other);

		addAll(other);
	}

	/**
	 * {@code ListNode} represents a node of the list that points to next and
	 * previous element in the collection. It also contains value.
	 *
	 */
	private static class ListNode {
		/**
		 * Pointer to the previous node.
		 */
		ListNode previous;
		/**
		 * Pointer to the next node.
		 */
		ListNode next;
		/**
		 * Value of the node.
		 */
		Object value;

		ListNode(Object value) {
			this.value = value;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return number of stored objects in this collection
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value value to be added
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value);

		if (first == null) {
			first = new ListNode(value);
			last = first;
		} else {
			last.next = new ListNode(value);
			last.next.previous = last;
			last = last.next;
		}

		size++;
	}

	/**
	 * Remove a single instance of the specified value from this collection if it
	 * contains at least one such value.
	 * 
	 * @param value value to be removed
	 * @return {@code true} if {@code value} was removed, {@code false} otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (!contains(value))
			return false;

		else if (first.value.equals(value)) {
			if (first.next == null) {
				first = last = null;
			} else {
				first = first.next;
				first.previous = null;
			}
			size--;
			return true;
		} else {
			for (ListNode node = first.next; node != null; node = node.next) {
				if (node.value.equals(value)) {
					node.previous.next = node.next;

					if (node.next != null) {
						node.next.previous = node.previous;
					}

					size--;
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns {@code true} only if the collection contains at least one
	 * {@code value}, as determined by {@code equals} method.
	 * 
	 * @param value value to be searched
	 * @return {@code true} if this collection contains {@code value}, {@code false}
	 *         otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null || size == 0)
			return false;

		for (ListNode node = first; node != null; node = node.next) {
			if (node.value.equals(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns node at the specified index. Starts searching from the beginning of
	 * the collection.
	 * 
	 * @param index index of the element searched for
	 * @return node at the specified index
	 */
	private ListNode searchStart(int index) {
		ListNode node = first;
		for (int i = 0; i < index; i++, node = node.next)
			;

		return node;
	}

	/**
	 * Returns node at the specified index. Starts searching from the end of the
	 * collection.
	 * 
	 * @param index index of the element searched for
	 * @return node at the specified index
	 */
	private ListNode searchEnd(int index) {
		ListNode node = last;
		for (int i = size - 1; i > index; i--, node = node.previous)
			;

		return node;
	}

	/**
	 * Returns element of this collection that is at the specified index.
	 *
	 * @param index index of the element to be returned
	 * @return element of this collection that is on the specified index
	 * @throws IndexOutOfBoundsException if {@code index} is not in [0, size-1]
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Index must be in in [0, size-1].");

		if (index >= size / 2) {
			return searchEnd(index).value;
		} else {
			return searchStart(index).value;
		}
	}

	/**
	 * Removes all of the elements from this collection. The collection will be
	 * empty after this method returns.
	 */
	@Override
	public void clear() {
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * Processes each value of the collection.
	 * 
	 * @param processor processor that processes each value of the collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}

	/**
	 * Inserts {@code value} into this collection at the specified position.
	 * 
	 * @param value value to be inserted
	 * @param position position to be inserted on
	 * 
	 * @throws IndexOutOfBoundsException if {@code position} is not in [0, size]
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Position must be in [0, size]");
		Objects.requireNonNull(value);

		ListNode newNode = new ListNode(value);
		if (position == size) {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		} else if (position == 0) {
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
		} else {

			ListNode nodeAtPosition;

			if (position >= size / 2) {
				nodeAtPosition = searchEnd(position);
			} else {
				nodeAtPosition = searchStart(position);
			}

			newNode.previous = nodeAtPosition.previous;
			newNode.next = nodeAtPosition;
			newNode.next.previous = newNode;
			newNode.previous.next = newNode;
		}

		size++;
	}

	/**
	 * Returns index on which {@code value} is stored in this collection if it
	 * contains that value, -1 otherwise.
	 *
	 * @param value value to be searched for
	 * @return index index on which {@code value} is stored, -1 otherwise
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;

		ListNode node = first;
		for (int i = 0; i < size; i++, node = node.next) {
			if (node.value.equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes the element on the specified index in this collection.
	 * 
	 * @param index index of the element to be removed
	 * 
	 * @throws IndexOutOfBoundsException if {@code index} is not in [0, size-1]
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Position must be in [0, size-1].");

		if (index == 0) {
			first = first.next;
			first.previous = null;
		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {

			ListNode nodeAtIndex;
			if (index >= size / 2) {
				nodeAtIndex = searchEnd(index);
			} else {
				nodeAtIndex = searchStart(index);
			}

			nodeAtIndex.previous.next = nodeAtIndex.next;
			nodeAtIndex.next.previous = nodeAtIndex.previous;
		}

		size--;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 * 
	 * @return an array containing all of the elements in this collection
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];

		if (size == 0) {
			return result;
		}

		ListNode node = first;
		for (int i = 0; i < size; i++, node = node.next) {
			result[i] = node.value;
		}

		return result;
	}
}
