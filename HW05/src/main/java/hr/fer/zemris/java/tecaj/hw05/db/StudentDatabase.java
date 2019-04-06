package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Represents a database of the students records. Each record contains first
 * name, last name, identification number and final Grade.
 * 
 * @author Filip Karacic
 *
 */
public class StudentDatabase {
	/**
	 * Hash table of the student records.
	 */
	private SimpleHashtable<String, StudentRecord> table;
	
	private List<StudentRecord> students;

	/**
	 * Initializes newly created {@code StudentDatabase} object representing
	 * database of the student records and adds all of the valid records from the
	 * given list to this database. Invalid records from the given list will be
	 * skipped and will not be added to this database.
	 * <p>
	 * 
	 * Valid record has the following form: jmbag - last name - first name - final
	 * grade.
	 * 
	 * @param records
	 *            list of records to be added to this database
	 * @throws NullPointerException
	 *             if {@code records} is <code>null</code>
	 */
	public StudentDatabase(List<String> records) {
		Objects.requireNonNull(records);

		table = new SimpleHashtable<>();
		students = new ArrayList<>();

		addRecords(records);
	}

	/**
	 * Adds all of the valid records from the given list to this database. Invalid
	 * records will be skipped.
	 * 
	 * @param records
	 *            list of records to be added to this database
	 */
	private void addRecords(List<String> records) {
		for (String record : records) {
			String[] attributes = record.split("\t+");

			if (validAttributes(attributes)) {
				StudentRecord student = new StudentRecord(attributes[2], attributes[1], attributes[0], attributes[3]);
				table.put(attributes[0], student);
				students.add(student);
			}
		}

	}

	/**
	 * Returns <code>true</code> if the given attributes are valid student record.
	 * 
	 * @param attributes
	 *            attributes of the student record
	 * @return {@code true} if the given attributes are valid student record
	 */
	private boolean validAttributes(String[] attributes) {
		if (attributes.length != 4)
			return false;

		String jmbag = attributes[0];

		if (jmbag.length() != 10)
			return false;

		for (int i = 0, h = 10; i < h; i++) {
			if (!Character.isDigit(jmbag.charAt(i)))
				return false;
		}

		String firstName = attributes[1];
		for (int i = 0, h = firstName.length(); i < h; i++) {
			if (!Character.isLetter(firstName.charAt(i)) && firstName.charAt(i) != '-' && firstName.charAt(i) != ' ')
				return false;
		}

		String lastName = attributes[2];
		for (int i = 0, h = lastName.length(); i < h; i++) {
			if (!Character.isLetter(lastName.charAt(i)) && lastName.charAt(i) != '-' && lastName.charAt(i) != ' ')
				return false;
		}

		String finalGrade = attributes[3];
		try {
			int grade = Integer.parseInt(finalGrade);

			if (grade <= 0 || grade > 5)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Returns student record with the given jmbag if there is one,
	 * <code>null</code> otherwise.
	 * 
	 * @param jmbag
	 *            identification number of the student
	 * 
	 * @return student record with the given jmbag if there is one,
	 *         <code>null</code> otherwise
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (jmbag == null)
			return null;

		return table.get(jmbag);
	}

	/**
	 * Filters this database with the given filter and return list of the student
	 * records that satisfy the given filter.
	 * 
	 * @param filter
	 *            filter for filtering students from this database
	 * 
	 * @return list of the student records from this database that satisfy the given
	 *         filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		Objects.requireNonNull(filter);

		List<StudentRecord> result = new ArrayList<>();
		
		for (StudentRecord record : students) {
	
			if (filter.accepts(record))
				result.add(record);
		}

		return result;
	}
}
