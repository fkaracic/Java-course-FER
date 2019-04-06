package hr.fer.zemris.java.hw03.structures;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class {@code ArrayIndexedCollection} represents a resizable-array
 * implementation of the collection and provides methods for work with
 * collection. When the collection is fulled with elements array is reallocated
 * with double of its previous capacity. Collection does not permit adding
 * {@code null}.
 * 
 * @author Filip Karacic
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Number of elements currently in the collection.
	 */
	private int size;
	
	/**
	 * Capacity of the collection.
	 */
	private int capacity;
	
	/**
	 * Array of {@code Object} containing elements of the collection.
	 */
	private Object[] elements;
	
	/**
	 * Default capacity of the array.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Initialize a newly created {@code ArrayIndexedCollection} object so that it
	 * represent an empty collection. Capacity of the collection is 16 elements.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Initialize a newly created {@code ArrayIndexedCollection} object so that it
	 * represent an empty collection. Capacity of the collection is defined by
	 * {@code initialCapacity}.
	 * 
	 * @param initialCapacity capacity of this collection
	 * 
	 * @throws IllegalArgumentException if {@code initialCapacity} is less or equal to 0.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException("Capacity must be greater than 0.");

		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * Initialize a newly created {@code ArrayIndexedCollection} object so that it
	 * represent an empty collection and adds elements from {@code other} collection
	 * to this collection. Default capacity of the collection is 16 elements. If
	 * {@code other} collection's size is bigger than 16, capacity of this
	 * collection is reallocated to the size of the {@code other} collection.
	 * 
	 * @param other collection with elements to be copied to this collection
	 * 
	 * @throws NullPointerException if {@code other} is {@code null}
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Initialize a newly created {@code ArrayIndexedCollection} object so that it
	 * represent an empty collection and adds elements from {@code other} collection
	 * to this collection. Capacity of the collection is defined by
	 * {@code initialCapacity}. If {@code other} collection's size is bigger than
	 * {@code initialCapacity}, capacity of this collection is reallocated to the
	 * size of the {@code other} collection.
	 * 
	 * @param other collection with elements to be copied to this collection
	 * @param initialCapacity capacity of this collection
	 * 
	 * @throws NullPointerException if {@code other} is {@code null}
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		Objects.requireNonNull(other);

		if (other.size() > initialCapacity) {
			capacity = other.size();
			elements = new Object[capacity];
			
		}else {
			capacity = initialCapacity;
			elements = new Object[capacity];
		}

		this.addAll(other);
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

		insert(value, size);
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
		if (value == null) return false;

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Remove a single instance of the specified value from this collection if it
	 * contains at least one such value.
	 * 
	 * @param value value to be removed
	 * 
	 * @return {@code true} if {@code value} was removed, {@code false} otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) throw new NullPointerException("Value cannot be null.");

		for (int i = 0; i < size - 1; i++) {
			if (elements[i].equals(value)) {
				for (int j = i; j < size - 1; j++) {
					elements[j] = elements[j + 1];
				}

				elements[size - 1] = null;
				size--;
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 * 
	 * @return an array containing all of the elements in this collection
	 */
	@Override
	public Object[] toArray() {
		Objects.requireNonNull(elements);

		return Arrays.copyOf(elements, size);
	}

	/**
	 * Processes each value of the collection.
	 * 
	 * @param processor processor that processes each value of the collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Returns element of this collection that is at the specified index.
	 * 
	 * @param index index of the element to be returned
	 * 
	 * @return element of this collection that is on the specified index
	 * 
	 * @throws IndexOutOfBoundsException if {@code index} is not in [0, size-1]
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index must be in [0, size-1].");

		return elements[index];
	}

	/**
	 * Removes all of the elements from this collection. The collection will be
	 * empty after this method returns.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
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

		if (size + 1 > capacity) {
			capacity = 2 * capacity;
			elements = Arrays.copyOf(elements, capacity);
		}

		for (int i = size - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}

		elements[position] = value;
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
		if (value == null) return -1;

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
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
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index must be in in [0, size-1].");

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;

		size--;
	}

}