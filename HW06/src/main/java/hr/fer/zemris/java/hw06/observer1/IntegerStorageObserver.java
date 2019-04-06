package hr.fer.zemris.java.hw06.observer1;

/**
 * A class can implement the {@code IntegerStorageObserver} interface when it
 * wants to be informed of changes in {@code Integer} objects.
 * 
 * @author Filip Karacic
 *
 */
public interface IntegerStorageObserver {

	/**
	 * This method is called whenever the observed object is changed.
	 * 
	 * @param istorage
	 *            value observed by the list of observers
	 * 
	 */
	public void valueChanged(IntegerStorage istorage);
}