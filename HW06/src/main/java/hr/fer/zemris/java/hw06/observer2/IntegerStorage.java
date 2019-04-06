package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents storage of integer values and provides adding observers for value
 * changes.
 * 
 * @author Filip Karacic
 *
 */
public class IntegerStorage {
	/**
	 * Integer value.
	 */
	private int value;
	/**
	 * List of observers.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Initializes newly created {@code IntegerStorage} object
	 * 
	 * @param initialValue
	 *            initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Adds the given observer to the list of observers.
	 * 
	 * @param observer
	 *            observer to be added
	 * @throws NullPointerException
	 *             if the given observer is <code>null</code>
	 */
	public void addObserver(IntegerStorageObserver observer) {
		observers.add(Objects.requireNonNull(observer));
	}

	/**
	 * Removes the given observer from the list of observers.
	 * 
	 * @param observer
	 *            observer to be removed
	 * @throws NullPointerException
	 *             if the given observer is <code>null</code>
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		observers.remove(observer);
	}

	/**
	 * Removes all of the observers from the list of observers. After this method is
	 * done, the list of observers will be empty.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Return value of this storage.
	 * 
	 * @return value of this storage
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Storage the given value to this value if the given value is not the exact
	 * same as the current value.
	 * 
	 * @param value
	 *            new value to storage
	 */
	public void setValue(int value) {

		if (this.value != value) {
			IntegerStorageChange iChange = new IntegerStorageChange(this, value);
			
			this.value = value;
			if (observers != null) {

				

				for (IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(iChange);
				}
			}
		}
	}
}