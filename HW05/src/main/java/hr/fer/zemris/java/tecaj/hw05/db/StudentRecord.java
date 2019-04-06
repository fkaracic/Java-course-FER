package hr.fer.zemris.java.tecaj.hw05.db;

import java.util.Objects;

/**
 * {@code StudentRecord} represents a record of the student defined with his
 * first name, last name, jmbag and final grade. Every student has his own
 * unique jmbag.
 * 
 * @author Filip Karacic
 *
 */
public class StudentRecord {
	/**
	 * First name of this student.
	 */
	private String firstName;
	/**
	 * Last name of this student.
	 */
	private String lastName;
	/**
	 * Identification number of this student.
	 */
	private String jmbag;
	/**
	 * Final grade of this student.
	 */
	private String finalGrade;

	/**
	 * Initializes newly created {@code StudentRecord} object representing a record
	 * of the student.
	 * 
	 * @param firstName
	 *            student's first name
	 * @param lastName
	 *            student's last name
	 * @param jmbag
	 *            student's identification number
	 * @param finalGrade
	 *            student's final grade
	 */
	public StudentRecord(String firstName, String lastName, String jmbag, String finalGrade) {
		this.firstName = Objects.requireNonNull(firstName);
		this.lastName = Objects.requireNonNull(lastName);
		this.jmbag = Objects.requireNonNull(jmbag);
		this.finalGrade = Objects.requireNonNull(finalGrade);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		StudentRecord other = (StudentRecord) obj;

		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Returns first name of this student.
	 * 
	 * @return first name of this student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name of this student.
	 * 
	 * @return last name of this student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns identification number of this student.
	 * 
	 * @return identification number of this student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns final grade of this student.
	 * 
	 * @return final grade of this student
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

}
