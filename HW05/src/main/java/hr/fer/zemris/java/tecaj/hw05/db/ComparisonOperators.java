package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.Objects;

/**
 * {@code ComparisonOperators} represents comparison operator for comparison of
 * two {@code String} objects. Supported operators are: LESS, LESS OR EQUALS,
 * GREATER, GREATER OR EQUALS, EQUALS, NOT EQUALS, LIKE.
 * 
 * @author Filip Karacic
 *
 */
public class ComparisonOperators {

	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String}
	 * lexicographically precedes the other given {@code String}.
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) < 0;
	};

	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String}
	 * lexicographically precedes the other given {@code String} or if they are
	 * equals.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) <= 0;
	};

	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String}
	 * lexicographically follows the other given {@code String}.
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) > 0;
	};

	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String}
	 * lexicographically follows the other given {@code String} or if they are
	 * equals.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) >= 0;
	};
	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String} is
	 * lexicographically equals to the other given {@code String}.
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) == 0;
	};
	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String}
	 * lexicographically follows or precedes the other given {@code String}, i.e. if
	 * they are not equals.
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		return s1.compareTo(s2) != 0;
	};
	
	/**
	 * {@code IComparisonOperator} object used for comparison of the two given
	 * strings and its method {@link IComparisonOperator#satisfied(String, String)
	 * satisfied} returns {@code true} if the first given {@code String} has a form
	 * of the given {@code String} pattern.
	 * <p>
	 * 
	 * The first argument of the
	 * {@link IComparisonOperator#satisfied(String, String) satisfied} method is
	 * string to be checked, and the second argument pattern to be checked. String
	 * pattern can contain a wildcard *. This character, if present, can occur at
	 * most once, but it can be at the beginning, at the end or somewhere in the
	 * middle.
	 * <p>
	 * 
	 * {@link IComparisonOperator#satisfied(String, String) satisfied} method throws
	 * {@link IllegalArgumentException} if more than one wildcards have been
	 * entered.
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2);
		
		if (!s2.contains("*"))
			return s1.equals(s2);

		int index;
		if ((index = s2.indexOf('*')) != s2.lastIndexOf('*'))
			throw new IllegalArgumentException("Multiple wildcards '*' are not allowed.");

		if (index == 0) {
			s2 = s2.replaceAll("\\*", "");

			try {
				if (s1.substring(s1.length() - s2.length(), s1.length()).equals(s2))
					return true;
			} catch (IndexOutOfBoundsException e) {

			}

		} else if (index == s2.length() - 1) {
			s2 = s2.replaceAll("\\*", "");
			try {
				if (s1.substring(0, s2.length()).equals(s2))
					return true;
			} catch (IndexOutOfBoundsException e) {

			}
		} else {
			if (s1.length() < s2.length() - 1)
				return false;

			String[] startEnd = s2.split("\\*");

			try {
				if (s1.substring(0, startEnd[0].length()).equals(startEnd[0])
						&& s1.substring(s1.length() - startEnd[1].length(), s1.length()).equals(startEnd[1]))
					return true;
			} catch (IndexOutOfBoundsException e) {

			}

		}
		return false;
	};
}
