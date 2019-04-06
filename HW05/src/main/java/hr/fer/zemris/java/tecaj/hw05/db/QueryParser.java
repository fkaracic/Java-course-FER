package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryParser.SimpleQueryParserException;

/**
 * {@code QueryParser} is used for parsing the given query.
 * <p>
 * Query must have form: field name - operator - string literal.
 * <p>
 * 
 * Following seven comparison operators are supported: >, <, >=, <=, =, !=,
 * LIKE. On the left side of a comparison operator a field name is required and
 * on the right side string literal.
 * <p>
 * 
 * Command, attribute name, operator, string literal and logical operator AND
 * can be separated by more than one tabs or spaces. However, space is not
 * needed between attribute and operator, and between operator and string
 * literal. Logical operator AND can be written with any casing: AND, and, AnD
 * etc is OK. Command names, attribute names and literals are case sensitive.
 * 
 * @author Filip Karacic
 *
 */
public class QueryParser {
	/**
	 * Simple parser for the query.
	 */
	private SimpleQueryParser parser;

	/**
	 * Initializes newly created {@code QueryParser} representing the parser for the
	 * given query.
	 * 
	 * @param query
	 *            query for the student records.
	 * @throws NullPointerException
	 *             if the given query is <code>null</code>
	 * @throws QueryParserException
	 *             if the given query is an empty or invalid.
	 */
	public QueryParser(String query) {
		Objects.requireNonNull(query);

		if (query.isEmpty())
			throw new QueryParserException("Empty query");

		try {
			parser = new SimpleQueryParser(query);
		} catch (SimpleQueryParserException e) {
			throw new QueryParserException(e);
		}
	}

	/**
	 * Returns <code>true</code> if the given query is direct. Direct query has a
	 * form: jmbag = "xxx".
	 * 
	 * @return {@code true} if the given query is direct
	 */
	public boolean isDirectQuery() {
		List<ConditionalExpression> list = parser.getQuery();

		return list.size() == 1 && list.get(0).getComparisonOperator() == ComparisonOperators.EQUALS
				&& list.get(0).getFieldValueGetter() == FieldValueGetters.JMBAG;
	}

	/**
	 * Returns {@code String} representing JMBAG if the given query is direct.
	 * Direct query has a form: jmbag = "xxx".
	 * 
	 * @return {@code String} representing JMBAG of the student
	 * @throws IllegalStateException
	 *             if the query was not direct
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery())
			throw new IllegalStateException("Query is not direct.");

		return parser.getQuery().get(0).getStringLiteral();
	}

	/**
	 * Returns a list of the conditional expressions received from the given query.
	 * 
	 * @return list of the conditional expressions received from the given query
	 */
	public List<ConditionalExpression> getQuery() {
		return parser.getQuery();
	}

}
