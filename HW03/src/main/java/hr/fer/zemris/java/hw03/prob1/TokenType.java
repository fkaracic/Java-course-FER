package hr.fer.zemris.java.hw03.prob1;

/**
 * {@code TokenType} represents tokens specific for this lexer. Token types are:
 * EOF (End of file), WORD, NUMBER and SYMBOL.
 * 
 * @author Filip Karacic
 *
 */
public enum TokenType {

	/**
	 * End of file.
	 */
	EOF, 
	
	/**
	 * Word.
	 */
	WORD, 
	
	/**
	 * Number.
	 */
	NUMBER, 
	
	/**
	 * Symbol.
	 */
	SYMBOL;
}
