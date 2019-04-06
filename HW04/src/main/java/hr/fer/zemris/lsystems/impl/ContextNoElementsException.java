package hr.fer.zemris.lsystems.impl;

/**
 * An {@code ContextNoElementsException} is thrown to indicate that the context contains
 * no elements.
 *
 * @author Filip Karacic
 * 
 */
public class ContextNoElementsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code ContextNoElementsException} with no detail message.
	 */
	public ContextNoElementsException() {
		super();
	}

	/**
	 * Constructs a {@code ContextNoElementsException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public ContextNoElementsException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code ContextNoElementsException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public ContextNoElementsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code ContextNoElementsException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public ContextNoElementsException(String message, Throwable cause) {
		super(message, cause);
	}

}
