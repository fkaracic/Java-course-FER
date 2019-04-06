package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code ElementFunction} is used for representation of function expressions.
 * 
 * @author Filip Karacic
 *
 */
public class ElementFunction extends Element {
	/**
	 * Name of this function.
	 */
	private String name;

	/**
	 * Initializing newly created object representing function expression.
	 * 
	 * @param name
	 *            name of the function
	 */
	public ElementFunction(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Returns name of this element represented by {@code String}.
	 * 
	 * @return name of this element as {@code String}
	 */
	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns name of this element.
	 * 
	 * @return name of this element
	 */
	public String getName() {
		return name;
	}
}
