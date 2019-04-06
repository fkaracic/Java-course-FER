package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * @author Filip Karacic
 *
 */
public class ElementOperator extends Element {
	/**
	 * Symbol of this element.
	 */
	private String symbol;

	/**
	 * Constructor for creating new operator element.
	 * 
	 * @param symbol symbol for this element
	 */
	public ElementOperator(String symbol) {
		this.symbol = Objects.requireNonNull(symbol);
	}

	/**
	 * Returns symbol of this element represented by {@code String}.
	 * 
	 * @return symbol of this element as {@code String}
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Returns symbol of this element.
	 * 
	 * @return symbol of this element
	 */
	public String getSymbol() {
		return symbol;
	}

}
