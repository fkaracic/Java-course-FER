package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryLexer.SimpleQueryLexerException;
import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryLexer.SimpleQueryToken;
import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryLexer.SimpleQueryTokenType;

/**
 * {@code SimpleQueryParser} represents parser for the simple query.
 * 
 * @author Filip Karacic
 *
 */
class SimpleQueryParser {
	/**
	 * Lexer used for tokenizaton.
	 */
	private SimpleQueryLexer lexer;
	/**
	 * List for storing conditional expressions from the query.
	 */
	private List<ConditionalExpression> list;

	/**
	 * Current field name getter.
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * Current string literal.
	 */
	private String stringLiteral;
	/**
	 * Current comparison operator.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Initializes newly created {@code SimpleQueryParser} object representing a
	 * parser of this query.
	 * 
	 * @param query
	 *            text to be parsed
	 * @throws NullPointerException
	 *             if {@code query} is {@code null}
	 * @throws SimpleQueryParserException
	 *             if invalid text was given for parsing.
	 */
	public SimpleQueryParser(String query) {
		Objects.requireNonNull(query);

		list = new ArrayList<>();
		lexer = new SimpleQueryLexer(query);
		
		try {
			parse(query);
		}catch(SimpleQueryLexerException e) {
			throw new SimpleQueryParserException(e);
		}
	}

	/**
	 * Parses the given text using lexer for lexical analysis.
	 * 
	 * @param query
	 *            text to be parsed
	 */
	private void parse(String query) {
		SimpleQueryToken token = lexer.nextToken();

		while (token.getTokenType() != SimpleQueryTokenType.EOF) {
			
			if (token.getTokenType() == SimpleQueryTokenType.AND) {
				token = lexer.nextToken();	
			}

			if (token.getTokenType() != SimpleQueryTokenType.VARIABLE || !validVariable(token))
				throw new SimpleQueryParserException();

			token = lexer.nextToken();

			if (token.getTokenType() != SimpleQueryTokenType.OPERATOR)
				throw new SimpleQueryParserException();

			operatorSet(token);

			token = lexer.nextToken();

			if (token.getTokenType() != SimpleQueryTokenType.STRING)
				throw new SimpleQueryParserException();

			stringLiteral = token.getValue();

			list.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));

			token = lexer.nextToken();

			if (token.getTokenType() != SimpleQueryTokenType.AND && token.getTokenType() != SimpleQueryTokenType.EOF)
				throw new SimpleQueryParserException();
			
		}

	}

	/**
	 * Returns list of conditional expressions from this query.
	 * 
	 * @return list of conditional expressions from this query
	 */
	public List<ConditionalExpression> getQuery() {
		return new ArrayList<>(list);
	}

	/**
	 * Sets current comparisonOperator to the value of the given token.
	 * 
	 * @param token
	 *            operator token
	 */
	private void operatorSet(SimpleQueryToken token) {
		switch (token.getValue()) {
		case "<":
			comparisonOperator = ComparisonOperators.LESS;
			break;
		case "<=":
			comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
			break;
		case ">":
			comparisonOperator = ComparisonOperators.GREATER;
			break;
		case ">=":
			comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
			break;
		case "=":
			comparisonOperator = ComparisonOperators.EQUALS;
			break;
		case "!=":
			comparisonOperator = ComparisonOperators.NOT_EQUALS;
			break;
		case "LIKE":
			comparisonOperator = ComparisonOperators.LIKE;
			break;
		}

	}

	/**
	 * Returns <code>true</code> if value of the given token is valid. Valid values
	 * are: "firstName", "lastName" and "jmbag".
	 * 
	 * @param token
	 *            variable token
	 * 
	 * @return <code>true</code> if value of the given token is valid
	 */
	private boolean validVariable(SimpleQueryToken token) {
		switch (token.getValue()) {
		case "firstName":
			fieldValueGetter = FieldValueGetters.FIRST_NAME;
			return true;
		case "lastName":
			fieldValueGetter = FieldValueGetters.LAST_NAME;
			return true;
		case "jmbag":
			fieldValueGetter = FieldValueGetters.JMBAG;
			return true;
		default:
			return false;
		}
	}

	/**
	 * Thrown if invalid text is given for parsing.
	 *
	 */
	class SimpleQueryParserException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a new {@code SimpleQueryParserException} with {@code null} as its
		 * detail message.
		 */
		public SimpleQueryParserException() {

		}

		/**
		 * Constructs a {@code SimpleQueryParserException} with the specified detail
		 * message.
		 *
		 * @param message
		 *            the detail message
		 */
		public SimpleQueryParserException(String message) {
			super(message);
		}

		/**
		 * Constructs a {@code SimpleQueryParserException} with the specified cause.
		 * 
		 * @param cause
		 *            the cause (which is saved for later retrieval. (A {@code null}
		 *            value is permitted, and indicates that the cause is nonexistent or
		 *            unknown).
		 */
		public SimpleQueryParserException(Throwable cause) {
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
		public SimpleQueryParserException(String message, Throwable cause) {
			super(message, cause);
		}

	}

}
