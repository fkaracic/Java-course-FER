package hr.fer.zemris.java.hw03.structures;

/**
 * An {@code EmptyStackException} is thrown to indicate that the stack contains
 * no elements.
 *
 * @author Filip Karacic
 * 
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code EmptyStackException} with no detail message.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
