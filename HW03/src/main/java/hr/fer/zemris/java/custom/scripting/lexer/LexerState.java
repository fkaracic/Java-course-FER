package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * {@code LexerState} represents states of the lexer. States are
 * DOCUMENT_TEXT AND TAG. In DOCUMENT_TEXT (i.e. outside of tags) parser must
 * accept only the following two escaping: \\ treat as \ and \{ treat as { Every
 * other sequence which starts with \ is invalid.
 * <p>
 * In strings parser must accept following escaping: \\ sequence treat as a
 * single string character \ and \" treat as a single string character " (and not
 * the end of the string). \n, \r and \t have its usual meaning (ascii 10, 13 and
 * 9). Every other sequence which starts with \ should be treated as invalid.
 * 
 * @author Filip Karacic
 *
 */
public enum LexerState {

	/**
	 * Document text state.
	 */
	DOCUMENT_TEXT, 
	
	/**
	 * Tag state.
	 */
	TAG;
}
