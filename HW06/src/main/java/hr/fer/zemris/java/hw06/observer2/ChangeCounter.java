package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer that prints number of changes of the observed value whenever the
 * observed value is changed.
 * 
 * @author Filip Karacic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counter of changes for this observer.
	 */
	private int changes;

	@Override
	public void valueChanged(IntegerStorageChange ichange) {
		changes++;

		System.out.println("Number of value changes since tracking: " + changes);
	}

}
