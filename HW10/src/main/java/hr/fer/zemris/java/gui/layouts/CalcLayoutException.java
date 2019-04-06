package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown if an error while creating calculator layout occurs.
 * 
 * @author Filip Karacic
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructs a new {@code CalcLayoutException} with {@code null} as its
     * detail message.
     */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructs a {@code CalcLayoutException} with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code CalcLayoutException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a {@code CalcLayoutException} with the specified detailed message
	 * and the specified cause.
	 * 
	 * @param message the detail message
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval. (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or
	 *            unknown).
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
