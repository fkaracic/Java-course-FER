package hr.fer.zemris.java.hw06.observer1;

import java.util.Objects;

/**
 * Observer that prints double of observed value whenever the observed value is
 * changed. It prints the double value only n times (where n is given in the
 * constructor) and must be positive integer.
 * 
 * @author Filip Karacic
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Number of printing for this observer.
	 */
	private int n;

	/**
	 * Initializes {@code DoubleValue} object representing an observer.
	 * 
	 * @param n
	 *            number of printing for this observer
	 */
	public DoubleValue(int n) {
		if (n < 1)
			throw new IllegalArgumentException("Given argument must be positive integer.");

		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		Objects.requireNonNull(istorage);

		System.out.println("Double value: " + 2 * istorage.getValue());

		n -= 1;

		if (n == 0) {
			istorage.removeObserver(this);
		}
	}

	/**
	 * Returns number of printing for this observer.
	 * 
	 * @return number of printing
	 */
	public int getN() {
		return n;
	}

}
