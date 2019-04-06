package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Program demonstrates work with the student database using stream API. Student
 * records are written in the 'studenti.txt' text file placed in the current
 * directory.
 * 
 * @author Filip Karacic
 *
 */
public class StudentDemo {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments as array of {@code String}
	 * @throws IOException
	 *             if reading text file 'studenti.txt' from current directory failes
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("studenti.txt"));
		List<StudentRecord> records = convert(lines);

		System.out.println(
				"Number of students having score greater than 25: " + returnScoreGreaterThan25(records) + "\n");

		System.out.println("Number of students having excellent(5) grade: " + returnNumberOfExcellents(records) + "\n");

		System.out.println("Students having excellent(5) grade:");
		for (StudentRecord record : returnListOfExcellents(records)) {
			System.out.println(record);
		}

		System.out.println("\nStudents having excellent(5) grade sorted by the greatest score:");
		for (StudentRecord record : returnSortedListOfExcellents(records)) {
			System.out.println(record);
		}

		System.out.println("\nJmbags of students that haven't passed:");
		for (String jmbag : returnListOfFailed(records)) {
			System.out.println(jmbag);
		}

		for (Entry<Integer, List<StudentRecord>> entry : groupStudentsByGrades(records).entrySet()) {
			System.out.println("\nStudents with grade: " + entry.getKey());
			for (StudentRecord student : entry.getValue()) {
				System.out.println(student);
			}
		}

		for (Entry<Integer, Integer> entry : returnNumberOfStudentsGroupedByGrades(records).entrySet()) {
			System.out.println("\nStudents with grade " + entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("\nStudents that passed: ");
		for (StudentRecord student : groupByFailedAndPassed(records).get(true)) {
			System.out.println(student);
		}

		System.out.println("\nStudents that failed: ");
		for (StudentRecord student : groupByFailedAndPassed(records).get(false)) {
			System.out.println(student);
		}
	}

	/**
	 * Returns list of student records converted from the list containing
	 * {@code String} reresenting one student record.
	 * 
	 * @param lines
	 *            list containing lines where each line is one student record
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> result = new ArrayList<>();

		for (String line : lines) {
			String[] record = line.split("\t");

			if (record.length != 7)
				throw new IllegalArgumentException();

			result.add(new StudentRecord(record[0], record[1], record[2], Double.parseDouble(record[3]),
					Double.parseDouble(record[4]), Double.parseDouble(record[5]), Integer.parseInt(record[6])));

		}

		return result;
	}

	/**
	 * Returns number of students that have score on all of the exams greater than
	 * 25
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students that have score on all of the exams greater than
	 *         25
	 */
	public static long returnScoreGreaterThan25(List<StudentRecord> records) {
		return records.stream()
				.filter(student -> student.getMidTermScore() + student.getFinalTermScore() + student.getLabScore() > 25)
				.count();
	}

	/**
	 * Returns number of students whose final grade is excellent(5).
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students whose final grade is excellent(5)
	 */
	public static long returnNumberOfExcellents(List<StudentRecord> records) {
		return records.stream()
				      .filter(student -> student.getGrade() == 5).count();
	}

	/**
	 * Returns list of students whose final grade is excellent(5).
	 * 
	 * @param records
	 *            list of student records
	 * @return list of students whose final grade is excellent(5)
	 */
	public static List<StudentRecord> returnListOfExcellents(List<StudentRecord> records) {
		return records.stream()
				      .filter(student -> student.getGrade() == 5)
				      .collect(Collectors.toList());
	}

	/**
	 * Returns list of students whose final grade is excellent(5) sorted by the
	 * greates score on all of the exams.
	 * 
	 * @param records
	 *            list of student records
	 * @return list of students whose final grade is excellent(5) sorted by the
	 *         greates score on all of the exams
	 */
	public static List<StudentRecord> returnSortedListOfExcellents(List<StudentRecord> records) {
		return records.stream()
				      .filter(student -> student.getGrade() == 5)
				      .sorted((s1, s2) -> {
				    	  				double s1Score = s1.getMidTermScore() + s1.getFinalTermScore() + s1.getLabScore();
				    	  				double s2Score = s2.getMidTermScore() + s2.getFinalTermScore() + s2.getLabScore();

				    	  				return Double.valueOf(s2Score).compareTo(s1Score);
				      					})
				      .collect(Collectors.toList());
	}

	/**
	 * Returns list of students whose failed this subject.
	 * 
	 * @param records
	 *            list of student records
	 * @return list of students whose failed this subject
	 */
	public static List<String> returnListOfFailed(List<StudentRecord> records) {
		return records.stream()
					  .filter(student -> student.getGrade() == 1)
					  .sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag()))
					  .map(student -> student.getJmbag())
					  .collect(Collectors.toList());
	}

	/**
	 * Returns map with list of students mapped to their final grade.
	 * 
	 * @param records
	 *            list of the student records
	 * @return map with list of students mapped to their final grade
	 */
	public static Map<Integer, List<StudentRecord>> groupStudentsByGrades(List<StudentRecord> records) {
		return records.stream()
					  .collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Returns map with number of students mapped to their final grade.
	 * 
	 * @param records
	 *            list of the student records
	 * @return map with number of students mapped to their final grade
	 */
	public static Map<Integer, Integer> returnNumberOfStudentsGroupedByGrades(List<StudentRecord> records) {
		return records.stream()
					  .collect(Collectors
					  .toMap(StudentRecord::getGrade, s -> 1, Integer::sum));
	}

	/**
	 * Returns map with list of student records map to passed or failed.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with list of student records map to passed or failed
	 */
	public static Map<Boolean, List<StudentRecord>> groupByFailedAndPassed(List<StudentRecord> records) {
		return records.stream()
					  .collect(Collectors.partitioningBy(student -> student.getGrade() > 1));
	}

}
