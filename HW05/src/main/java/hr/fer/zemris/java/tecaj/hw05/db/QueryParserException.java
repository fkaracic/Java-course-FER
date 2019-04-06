package hr.fer.zemris.java.tecaj.hw05.db;

/**
 * Thrown if invalid text is given for parsing.
 * 
 * @author Filip Karacic
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code QueryParserException} with {@code null} as its
	 * detail message.
	 */
	public QueryParserException() {

	}

	/**
	 * Constructs a {@code QueryParserException} with the specified detail
	 * message.
	 *
	 * @param message
	 *            the detail message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code QueryParserException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code QueryParserException} with the specified detailed
	 * message and the specified cause.
	 * 
	 * @param message
	 *            the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
