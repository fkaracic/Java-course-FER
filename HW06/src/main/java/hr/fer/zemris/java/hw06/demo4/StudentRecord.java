package hr.fer.zemris.java.hw06.demo4;

import java.util.Objects;

/**
 * Represents a single record of the student. Record contains: Jmbag
 * (identification number), last name, first name, score on the mid-term exam,
 * score on the final-term exam, score on the lab-exams and the final grade.
 * 
 * @author Filip Karacic
 *
 */
public class StudentRecord {

	/**
	 * Identification number of this student.
	 */
	private String jmbag;
	/**
	 * Last name of this student.
	 */
	private String lastName;
	/**
	 * First name of this student.
	 */
	private String firstName;
	/**
	 * Score on the mid-term exam.
	 */
	private double midTermScore;
	/**
	 * Score on the final-term exam.
	 */
	private double finalTermScore;
	/**
	 * Score on the lab-exams.
	 */
	private double labScore;
	/**
	 * Final grade of this student.
	 */
	private int grade;

	/**
	 * Initializes newly created {@code StudentRecord} object representing a single
	 * student record.
	 * 
	 * @param jmbag
	 *            identification number
	 * @param lastName
	 *            last name
	 * @param firstName
	 *            first name
	 * @param midTermScore
	 *            score on the mid-term exam
	 * @param finalTermScore
	 *            score on the final-term exam
	 * @param labScore
	 *            score on the lab-exams
	 * @param grade
	 *            final grade
	 * 
	 * @throws NullPointerException
	 *             if any of the given arguments is null.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midTermScore, double finalTermScore,
			double labScore, int grade) {
		this.jmbag = Objects.requireNonNull(jmbag, "Jmbag must not be null");
		this.lastName = Objects.requireNonNull(lastName, "Last name must not be null");
		this.firstName = Objects.requireNonNull(firstName, "First name must not be null");

		if (!validScores(midTermScore, finalTermScore, labScore, grade))
			throw new IllegalArgumentException(
					"Grade must be within [1,5] and scores on exams must be non-negative double values whose sum is within [0,100].");

		this.midTermScore = midTermScore;
		this.finalTermScore = finalTermScore;
		this.labScore = labScore;
		this.grade = grade;
	}

	/**
	 * Returns <code>true</code> if the given grade and exam results are valid.
	 * 
	 * @param midTermScore
	 *            score on the mid-term exam
	 * @param finalTermScore
	 *            score on the final-term exam
	 * @param labScore
	 *            score on the lab-exams
	 * @param grade
	 *            final grade
	 * @return <code>true</code> if the given grade and exam results are valid
	 */
	private boolean validScores(double midTermScore, double finalTermScore, double labScore, int grade) {
		return grade >= 1 && grade <= 5 && midTermScore >= 0 && finalTermScore >= 0 && labScore >= 0
				&& midTermScore + finalTermScore + labScore <= 100;

	}

	/**
	 * Returns jmbag of this student.
	 * 
	 * @return jmbag of this student
	 */
	public String getJmbag() {
		return jmbag;
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
	 * Returns first name of this student.
	 * 
	 * @return first name of this student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns score of the mid-term exam of this student.
	 * 
	 * @return score of the mid-term exam of this student
	 */
	public double getMidTermScore() {
		return midTermScore;
	}

	/**
	 * Returns score of the final-term exam of this student.
	 * 
	 * @return score of the final-term exam of this student
	 */
	public double getFinalTermScore() {
		return finalTermScore;
	}

	/**
	 * Returns score of the lab-exams of this student.
	 * 
	 * @return score of the lab-exams of this student
	 */
	public double getLabScore() {
		return labScore;
	}

	/**
	 * Returns final grade of this student.
	 * 
	 * @return final grade of this student
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + midTermScore + " " + finalTermScore + " " + labScore
				+ " " + grade;
	}
}
