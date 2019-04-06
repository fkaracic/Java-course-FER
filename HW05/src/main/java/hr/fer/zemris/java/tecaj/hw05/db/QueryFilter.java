package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code QueryFilter} represents a filter of the student records.
 * 
 * @author Filip Karacic
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * List of the conditional expressions used for filtering.
	 */
	private List<ConditionalExpression> list;

	/**
	 * Initializes newly created {@code QueryFilter} object used for the filtering
	 * of the student records with the given conditional expressions. If the given
	 * list is empty method {@link #accepts(StudentRecord) accepts} will always
	 * return true.
	 * 
	 * @param list
	 *            list of the conditional expressions used for filtering
	 * 
	 * @throws NullPointerException
	 *             if the {@code list} is {@code null}
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		Objects.requireNonNull(list);

		this.list = new ArrayList<>(list);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		if(record == null) return false;
		
		for (ConditionalExpression expression : list) {
			if (!expression.getComparisonOperator().satisfied(expression.getFieldValueGetter().get(record),
					expression.getStringLiteral()))
				return false;
		}

		return true;
	}

}
