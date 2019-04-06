package hr.fer.zemris.java.gui.prim;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Represents model of the prime numbers.
 * 
 * @author Filip Karacic
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * Current prime.
	 */
	private int current;
	/**
	 * List of primes.
	 */
	private List<Integer> primes;
	/**
	 * List of listeners for this model.
	 */
	private List<ListDataListener> listeners;

	/**
	 * Constructor for this model.
	 */
	public PrimListModel() {
		listeners = new ArrayList<>();
		primes = new ArrayList<>();

		primes.add(current = 1);
	}

	/**
	 * Calculates next prime and notifies every listener that there is new value.
	 */
	public void next() {
		current = calculateNext(current);

		primes.add(current);

		notifyChange();
	}

	/**
	 * Notifies every listener that there is new value.
	 */
	private void notifyChange() {
		for (ListDataListener listener : listeners) {
			listener.intervalAdded(new ListDataEvent(primes, ListDataEvent.INTERVAL_ADDED, getSize(), getSize()));
		}

	}

	/**
	 * Calculates next prime number, i.e. first prime number bigger than current.
	 * 
	 * @param current current prime number.
	 * @return next prime number
	 */
	private int calculateNext(int current) {
		int prim = current;
		boolean isPrim;

		while (true) {
			isPrim = true;
			prim++;

			if (prim > 2 && prim % 2 == 0)
				continue;

			for (long i = 2; i <= sqrt(prim); i++) {
				if (prim % i == 0) {
					isPrim = false;
					break;
				}
			}

			if (isPrim)
				return prim;
		}
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(Objects.requireNonNull(listener));

	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);

	}

}
