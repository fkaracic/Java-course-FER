package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.Objects;

/**
 * {@code ConditionalExpression} represents an expression defined by the field
 * name, operator and string literal.
 * 
 * @author Filip Karacic
 *
 */
public class ConditionalExpression {

	/**
	 * {@code IFieldValueGetter} object representing a field name of a student in
	 * this expression.
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * String literal in this expression.
	 */
	private String stringLiteral;
	/**
	 * Comparison operator in this expression.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Initializes newly created {@code ConditionalExpression} object representing a
	 * conditional expression.
	 * 
	 * @param fieldValueGetter field name in the expression
	 * @param literal string literal in the expression
	 * @param comparisonOperator comparsion operator in the expression
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = Objects.requireNonNull(fieldValueGetter);
		this.stringLiteral = Objects.requireNonNull(literal);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}

	/**
	 * Returns the field name of this expression.
	 * 
	 * @return the field name of this expression
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Returns the string literal of this expression.
	 * 
	 * @return the string literal of this expression
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns the comparison operator of this expression.
	 * 
	 * @return the comparison operator of this expression
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
