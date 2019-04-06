package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * {@code TokenType} represents tokens specific for this lexer. Token types are:
 * EOF (End of file), TEXT, TAGSTART, TAGEND, VARIABLE, OPERATOR, FUNCTION, STRING, NUMBER.
 * 
 * @author Filip Karacic
 *
 */
public enum TokenType {
	
	/**
	 * Text.
	 */
	TEXT, 
	
	/**
	 * Start of the tag.
	 */
	TAGSTART, 
	
	/**
	 * End of the tag.
	 */
	TAGEND, 
	
	/**
	 * Variable.
	 */
	VARIABLE, 
	
	/**
	 * Operator.
	 */
	OPERATOR, 
	
	/**
	 * Function.
	 */
	FUNCTION, 
	
	/**
	 * String.
	 */
	STRING, 
	
	/**
	 * Number.
	 */
	NUMBER, 
	
	/**
	 * End of file.
	 */
	EOF;
}
