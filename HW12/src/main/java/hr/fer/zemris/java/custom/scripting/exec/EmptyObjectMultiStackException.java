package hr.fer.zemris.java.custom.scripting.exec;

/**
 * An {@code EmptyObjectMultiStackException} is thrown to indicate that the
 * "multistack" contains no elements.
 *
 * @author Filip Karacic
 * 
 */
public class EmptyObjectMultiStackException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code EmptyObjectMultiStackException} with no detail message.
	 */
	public EmptyObjectMultiStackException() {
	}

	/**
	 * Constructs an {@code EmptyObjectMultiStackException} with the specified
	 * detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public EmptyObjectMultiStackException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code EmptyObjectMultiStackException} with the specified
	 * cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public EmptyObjectMultiStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code EmptyObjectMultiStackException} with the specified
	 * detailed message and the specified cause.
	 * 
	 * @param message
	 *            the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public EmptyObjectMultiStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
