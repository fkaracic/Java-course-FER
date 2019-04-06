package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * Observer that prints square of observed value whenever the observed value is
 * changed.
 * 
 * @author Filip Karacic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange ichange) {
		int value = Objects.requireNonNull(ichange).getNewValue();

		System.out.println("Provided new value: " + value + ", square is " + value * value);
	}

}
