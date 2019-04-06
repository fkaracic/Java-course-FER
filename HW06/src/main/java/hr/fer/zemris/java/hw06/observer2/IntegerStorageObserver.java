package hr.fer.zemris.java.hw06.observer2;

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
	 * @param ichange
	 *            provider of information about source, previous and current value.
	 * 
	 */
	public void valueChanged(IntegerStorageChange ichange);
}