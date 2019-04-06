package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@code ElementConstantInteger} is used for representation of {@code int}
 * value expressions.
 * 
 * @author Filip Karacic
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Value of this element.
	 */
	private int value;

	/**
	 * Initialize newly created {@code ElementConstantInteger} object so that it
	 * represents expression with integer value.
	 * 
	 * @param value
	 *            value of this element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns value of this element represented by {@code String}.
	 * 
	 * @return value of this element as {@code String}
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Returns value of this element.
	 * 
	 * @return value of this element
	 */
	public double getValue() {
		return value;
	}
}
