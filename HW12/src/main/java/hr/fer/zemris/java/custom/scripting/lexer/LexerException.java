package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Thrown when invalid expression is given to the lexer for lexical analysis.
 * 
 * @author Filip Karacic
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructs a new {@code LexerException} with {@code null} as its
     * detail message.
     */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructs a {@code LexerException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code LexerException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code LexerException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
