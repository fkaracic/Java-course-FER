package hr.fer.zemris.java.namebuilder.lexer;

/**
 * {@code TokenType} represents tokens specific for this lexer. Token types are:
 * EOF (End of file), TEXT, SUBSTART, SUBEND, GROUPNUM, GROUPDESC.
 * 
 * @author Filip Karacic
 *
 */
public enum TokenType {

	/**
	 * Plain text.
	 */
	TEXT,

	/**
	 * Substitution start.
	 */
	SUBSTART,

	/**
	 * Substitution end.
	 */
	SUBEND,

	/**
	 * Group number.
	 */
	GROUPNUM,

	/**
	 * Description of the group.
	 */
	GROUPDESC,

	/**
	 * End of the file.
	 */
	EOF;
}
