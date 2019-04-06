package hr.fer.zemris.java.tecaj.hw05.db;

/**
 * Represents a function that receives a {@code StudentRecord} and checks if
 * record satisfies the filter.
 * 
 * @author Filip Karacic
 *
 */
@FunctionalInterface
public interface IFilter {

	/**
	 * Filters the given record and returns {@code true} if record satisfies the
	 * filter.
	 * 
	 * @param record
	 *            student record to be filtered
	 * @return {@code true} if the given record satisfies the filter, {@code false}
	 *         otherwise
	 */
	public boolean accepts(StudentRecord record);
}
