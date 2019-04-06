package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code ElementVariable} is used for representation of variables.
 * 
 * @author Filip Karacic
 *
 */
public class ElementVariable extends Element {
	/**
	 * Name of this variable.
	 */
	private String name;

	/**
	 * Initializing newly created object representing variable.
	 * 
	 * @param name
	 *            name of the variable
	 */
	public ElementVariable(String name) {
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
	public String getValue() {
		return name;
	}

}
