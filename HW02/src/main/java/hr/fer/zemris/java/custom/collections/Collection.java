package hr.fer.zemris.java.custom.collections;

/**
 * {@code Collection} represents some general collection of objects. Methods of
 * this class is not implemented so class that extends this class must implement
 * them.
 * 
 * @author Filip Karacic
 *
 */
public class Collection {

	/**
	 * Initialize a newly created {@code Collection} object.
	 */
	protected Collection() {
	}

	/**
	 * Returns {@code true} if collection contains no elements, {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if collection has no elements, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * Implemented here to always return 0. Must be implemented in the class that
	 * extends {@code Collection} class.
	 * 
	 * @return number of stored objects in this collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection. Implemented here to do nothing.
	 * Must be implemented in the class that extends {@code Collection} class.
	 * 
	 * @param value value to be added
	 */
	public void add(Object value) {
	}

	/**
	 * Returns {@code true} only if the collection contains at least one
	 * {@code value}, as determined by {@code equals} method.
	 * 
	 * @param value value to be searched
	 * @return {@code true} if this collection contains {@code value}, {@code false}
	 *         otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Remove a single instance of the specified value from this collection if it
	 * contains at least one such value. Implemented here to always return false.
	 * Must be implemented in the class that extends {@code Collection} class.
	 * 
	 * @param value value to be removed
	 * @return {@code true} if {@code value} was removed, {@code false} otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 * Implemented here to always throw {@link UnsupportedOperationException}.
	 * 
	 * @return an array containing all of the elements in this collection
	 * 
	 * @throws UnsupportedOperationException with every call of this method
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Processes each value of the collection. Implemented here to do nothing. Must
	 * be implemented in the class that extends {@code Collection} class.
	 * 
	 * @param processor processor that processes each value of the collection
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds all of the elements in the {@code other} collection to this collection.
	 * 
	 * @param other collection with elements to be added to this collection
	 */
	public void addAll(Collection other) {
		class Process extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}

		Process processor = new Process();
		other.forEach(processor);
	}

	/**
	 * Removes all of the elements from this collection. The collection will be
	 * empty after this method returns. Implemented here to do nothing. Must be
	 * implemented in the class that extends {@code Collection} class.
	 */
	public void clear() {
	}
}
