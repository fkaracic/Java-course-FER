package hr.fer.zemris.java.tecaj.hw05.db;

/**
 * Represents a getter for the specified value of the student record.
 * 
 * @author Filip Karacic
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * Returns the specified value of the given student record as {@code String}.
	 * 
	 * @param record
	 *            record of the student
	 * @return the specified value of the given student record as {@code String}
	 * 
	 * @throws NullPointerException
	 *             if the given record is <code>null</code>
	 */
	public String get(StudentRecord record);
}
