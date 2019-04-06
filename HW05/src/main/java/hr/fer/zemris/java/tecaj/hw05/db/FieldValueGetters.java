package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.Objects;

/**
 * Represents getters for the field value of the student record. Supported
 * getter are: FIRST NAME, LAST NAME and JMBAG.
 * 
 * @author Filip Karacic
 *
 */
public class FieldValueGetters {

	/**
	 * Getter for the first name of the student record.
	 */
	public static final IFieldValueGetter FIRST_NAME = rec -> {
		Objects.requireNonNull(rec);
		return rec.getFirstName();
	};

	/**
	 * Getter for the last name of the student record.
	 */
	public static final IFieldValueGetter LAST_NAME = rec -> {
		Objects.requireNonNull(rec);
		return rec.getLastName();
	};
	
	/**
	 * Getter for the jmbag of the student record.
	 */
	public static final IFieldValueGetter JMBAG = rec -> {
		Objects.requireNonNull(rec);
		return rec.getJmbag();
	};
}
