package hr.fer.zemris.java.hw07.shell;

/**
 * Thrown when reading or writing fails.
 * 
 * @author Filip Karacic
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructs a new {@code ShellIOException} with {@code null} as its
     * detail message.
     */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Constructs a {@code ShellIOException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code ShellIOException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code ShellIOException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
