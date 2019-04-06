package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@code Element} is a base class and is used for representation of
 * expressions.
 * 
 * @author Filip Karacic
 *
 */
public class Element {

	/**
	 * Returns element represented by {@code String}. Implemented here to return an
	 * empty {@code String}. Class extending this class must implement it.
	 * 
	 * @return {@code String} representing this element
	 */
	public String asText() {
		return "";
	}
}
