package hr.fer.zemris.java.namebuilder.lexer;

import java.util.Objects;

/**
 * {@code Token} represents a single token created from lexical analysis. A
 * lexical token or simply token is a string with an assigned and thus
 * identified meaning. It is structured as a pair consisting of a token name and
 * an optional token value.
 * 
 * @author Filip Karacic
 *
 */
public class Token {

	/**
	 * Type of this token.
	 */
	private TokenType type;
	/**
	 * value of this token representing an expression.
	 */
	private String value;

	/**
	 * Initializes newly created object representing a token.
	 * 
	 * @param type
	 *            type of this token
	 * @param value
	 *            value of this token
	 */
	public Token(TokenType type, String value) {
		this.type = Objects.requireNonNull(type);
		this.value = value;
	}

	/**
	 * Returns value of this token.
	 * 
	 * @return value of this token
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns type of this token.
	 * 
	 * @return type of this token
	 */
	public TokenType getType() {
		return type;
	}

}