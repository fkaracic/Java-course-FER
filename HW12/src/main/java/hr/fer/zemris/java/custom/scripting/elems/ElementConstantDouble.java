package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@code ElementConstantDouble} is used for representation of {@code double}
 * value expressions.
 * 
 * @author Filip Karacic
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * Value of this element.
	 */
	private double value;

	/**
	 * Initialize newly created {@code ElementConstantDouble} object so that it
	 * represents expression with double value.
	 * 
	 * @param value
	 *            value of this element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Returns value of this element represented by {@code String}.
	 * 
	 * @return value of this element as {@code String}
	 */
	@Override
	public String asText() {
		return Double.toString(value);
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
