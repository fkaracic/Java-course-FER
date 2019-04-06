package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code ElementString} is used for representation of string expressions.
 * 
 * @author Filip Karacic
 *
 */
public class ElementString extends Element {
	/**
	 * Value of this element.
	 */
	private String value;

	/**
	 * Initializing newly created object representing string expression.
	 * 
	 * @param value
	 *            value of the string
	 */
	public ElementString(String value) {
		this.value = Objects.requireNonNull(value);
	}

	/**
	 * Returns value of this element represented by {@code String}.
	 * 
	 * @return value of this element as {@code String}
	 */
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Returns value of this element.
	 * 
	 * @return value of this element
	 */
	public String getValue() {
		return value;
	}
}
