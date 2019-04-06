package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * {@code IntegerStorageChange} class provides information about source of the
 * value and previous and current value.
 * 
 * @author Filip Karacic
 *
 */
public class IntegerStorageChange {

	/**
	 * Source of the value.
	 */
	private IntegerStorage istorage;
	/**
	 * Previous value.
	 */
	private int valueBeforeChanging;
	/**
	 * Current value.
	 */
	private int newValue;

	/**
	 * Initializes newly created {@code IntegerStorageChange} object representing
	 * information provider (provides information about source of the value and
	 * previous and current value).
	 * 
	 * @param istorage
	 *            source of the value
	 * @param value
	 *            new value for storage
	 */
	public IntegerStorageChange(IntegerStorage istorage, int value) {
		this.istorage = Objects.requireNonNull(istorage);
		valueBeforeChanging = istorage.getValue();
		newValue = value;
	}

	/**
	 * Returns source of the value.
	 * 
	 * @return source of the value
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Returns the previous value.
	 * 
	 * @return the previous value
	 */
	public int getValueBeforeChanging() {
		return valueBeforeChanging;
	}

	/**
	 * Returns the current value.
	 * 
	 * @return the current value
	 */
	public int getNewValue() {
		return newValue;
	}

}
