package hr.fer.zemris.java.tecaj.hw05.db;

/**
 * Represents a comparison operator that compares two {@code String} objects and
 * returns the result of that comparison as {@code true} or {@code false}.
 * 
 * @author Filip Karacic
 */
@FunctionalInterface
public interface IComparisonOperator {

	/**
	 * Compares the two given {@code String} objects and returns the result of the
	 * comparison for this operator.
	 * 
	 * @param value1
	 *            the first value for comparison
	 * @param value2
	 *            the second value for comparison
	 * @return <code>true</code> if comparison is satisfied
	 * 
	 * @throws NullPointerException
	 *             if any of the given arguments is <code>null</code>
	 */
	boolean satisfied(String value1, String value2);
}
