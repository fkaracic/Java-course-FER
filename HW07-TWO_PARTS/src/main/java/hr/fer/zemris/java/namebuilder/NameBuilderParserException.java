package hr.fer.zemris.java.namebuilder;

/**
 * Thrown if invalid text is given for parsing.
 * 
 * @author Filip Karacic
 *
 */
public class NameBuilderParserException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructs a new {@code NameBuilderParserException} with {@code null} as its
	     * detail message.
	     */
		public NameBuilderParserException() {
			super();
		}

	/**
		 * Constructs a {@code NameBuilderParserException} with the specified detail message.
		 *
		 * @param message the detail message
		 */
		public NameBuilderParserException(String message) {
			super(message);
		}

	/**
		 * Constructs a {@code NameBuilderParserException} with the specified cause.
		 * 
		 * @param cause
		 *            the cause (which is saved for later retrieval. (A {@code null}
		 *            value is permitted, and indicates that the cause is nonexistent or
		 *            unknown).
		 */
		public NameBuilderParserException(Throwable cause) {
			super(cause);
		}

	/**
		 * Constructs a {@code NameBuilderParserException} with the specified detailed message
		 * and the specified cause.
		 * 
		 * @param message the detail message
		 * 
		 * @param cause
		 *            the cause (which is saved for later retrieval. (A {@code null}
		 *            value is permitted, and indicates that the cause is nonexistent or
		 *            unknown).
		 */
		public NameBuilderParserException(String message, Throwable cause) {
			super(message, cause);
		}

}
