package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown if invalid text is given for parsing.
 * 
 * @author Filip Karacic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	/** Constructs a new {@code SmartScriptParserException} with {@code null} as its
     * detail message.
     */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructs a {@code SmartScriptParserException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code SmartScriptParserException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code SmartScriptParserException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
