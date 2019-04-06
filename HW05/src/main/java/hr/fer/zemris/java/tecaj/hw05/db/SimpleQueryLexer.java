package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.Objects;

/**
 * {@code SimpleQueryLexer} represents lexical analysis,i.e. the process of
 * converting a sequence of characters into a sequence of tokens. Text contains
 * sequences of variables, operators and literal strings. Token of type EOF is
 * generated as last one in processing.
 * 
 * @author Filip Karacic
 *
 */
class SimpleQueryLexer {
	/**
	 * Input text.
	 */
	private char[] data;
	/**
	 * Index of the first unprocessed sign.
	 */
	private int index;
	/**
	 * Current token.
	 */
	private SimpleQueryToken token;

	/**
	 * Initialize newly created object representing a lexer performing a lexical
	 * analysis.
	 * 
	 * @param text
	 *            text to be analysed lexically
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public SimpleQueryLexer(String text) {
		Objects.requireNonNull(text);

		data = text.toCharArray();
	}

	/**
	 *
	 * Returns the next token after lexical analysis. Returns token of type EOF if
	 * end of input is reached.
	 * 
	 * @return next token after lexical analysis
	 * @throws SimpleQueryLexerException
	 *             If lexically invalid input is given or if method is called after
	 *             the end of file
	 */
	public SimpleQueryToken nextToken() {
		if (token != null && token.tokenType == SimpleQueryTokenType.EOF)
			throw new SimpleQueryLexerException();

		skipBlanks();

		if (index >= data.length)
			return token = new SimpleQueryToken(SimpleQueryTokenType.EOF, null);

		if (Character.isLetter(data[index])) {
			return textExtract();
		}

		if (isOperator()) {
			return operatorExtract();
		}

		if (data[index] == '\"') {
			return stringExtract();
		}

		else
			throw new SimpleQueryLexerException();
	}

	/**
	 * Returns token of type operator.
	 * 
	 * @return operator token
	 * @throws SimpleQueryLexerException
	 *             if invalid sequence of operators is given
	 */
	private SimpleQueryToken operatorExtract() {
		String operator = String.valueOf(data[index++]);

		if (index < data.length && isOperator()) {
			operator += String.valueOf(data[index++]);
		}

		switch (operator) {
		case "<":
		case "<=":
		case ">":
		case ">=":
		case "=":
		case "!=":
			return token = new SimpleQueryToken(SimpleQueryTokenType.OPERATOR, operator);
		default:
			throw new SimpleQueryLexerException();
		}
	}

	/**
	 * Returns token of type literal string.
	 * 
	 * @return literal string token
	 * @throws SimpleQueryLexerException
	 *             if invalid literal string sequence is given
	 */
	private SimpleQueryToken stringExtract() {
		StringBuilder string = new StringBuilder();
		index++;

		while (index < data.length && data[index] != '\"') {
			string.append(data[index++]);
		}

		if (index >= data.length || data[index] != '\"')
			throw new SimpleQueryLexerException();

		index++;

		return token = new SimpleQueryToken(SimpleQueryTokenType.STRING, string.toString());
	}

	/**
	 * Returns token of the type AND if the given text is "and" ignoring the case,
	 * type LIKE if the given text is "LIKE", else the type VARIABLE.
	 * 
	 * @return literal string token
	 */
	private SimpleQueryToken textExtract() {
		StringBuilder text = new StringBuilder();

		while (index < data.length && Character.isLetter(data[index])) {
			text.append(data[index++]);
		}
		if (text.toString().equalsIgnoreCase("and"))
			return token = new SimpleQueryToken(SimpleQueryTokenType.AND, "and");

		if (text.toString().equals("LIKE"))
			return token = new SimpleQueryToken(SimpleQueryTokenType.OPERATOR, "LIKE");

		return token = new SimpleQueryToken(SimpleQueryTokenType.VARIABLE, text.toString());

	}

	/**
	 * Returns <code>true</code> if the {@code char} at the current index is valid
	 * operator
	 * 
	 * @return <code>true</code> if the {@code char} at the current index is valid
	 *         operator
	 */
	private boolean isOperator() {
		switch (data[index]) {
		case '!':
		case '<':
		case '=':
		case '>':
			return true;
		default:
			return false;
		}
	}

	/**
	 * Skips blanks in the given text.
	 */
	private void skipBlanks() {
		while (index < data.length) {
			switch (data[index]) {
			case ' ':
			case '\t':
				break;
			default:
				return;
			}
			index++;
		}
	}

	/**
	 * {@code SimpleQueryToken} represents a single token created from lexical
	 * analysis. A lexical token or simply token is a string with an assigned and
	 * thus identified meaning. It is structured as a pair consisting of a token
	 * name and {@code String} value.
	 *
	 */
	static class SimpleQueryToken {
		/**
		 * Type of the token.
		 */
		private SimpleQueryTokenType tokenType;
		/**
		 * Value of the token.
		 */
		private String value;

		/**
		 * Initializes newly created object representing a token.
		 * 
		 * @param tokenType
		 *            type of this token
		 * @param value
		 *            value of this token
		 */
		public SimpleQueryToken(SimpleQueryTokenType tokenType, String value) {
			this.tokenType = Objects.requireNonNull(tokenType);
			this.value = value;
		}

		/**
		 * Returns type of this token.
		 * 
		 * @return type of this token
		 */
		public SimpleQueryTokenType getTokenType() {
			return tokenType;
		}

		/**
		 * Returns value of this token.
		 * 
		 * @return value of this token
		 */
		public String getValue() {
			return value;
		}

	}

	/**
	 * Represents type of the token.
	 *
	 */
	static enum SimpleQueryTokenType {
		VARIABLE, OPERATOR, AND, STRING, EOF;
	}

	/**
	 * Thrown if invalid text is given for tokenization.
	 */
	static class SimpleQueryLexerException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a new {@code SimpleQueryLexerException} with {@code null} as its
		 * detail message.
		 */
		public SimpleQueryLexerException() {

		}

		/**
		 * Constructs a {@code SimpleQueryLexerException} with the specified detail
		 * message.
		 *
		 * @param message
		 *            the detail message
		 */
		public SimpleQueryLexerException(String message) {
			super(message);
		}

		/**
		 * Constructs a {@code SimpleQueryLexerException} with the specified cause.
		 * 
		 * @param cause
		 *            the cause (which is saved for later retrieval. (A {@code null}
		 *            value is permitted, and indicates that the cause is nonexistent or
		 *            unknown).
		 */
		public SimpleQueryLexerException(Throwable cause) {
			super(cause);
		}

		/**
		 * Constructs a {@code SimpleQueryLexerException} with the specified detailed
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
		public SimpleQueryLexerException(String message, Throwable cause) {
			super(message, cause);
		}

	}
}
