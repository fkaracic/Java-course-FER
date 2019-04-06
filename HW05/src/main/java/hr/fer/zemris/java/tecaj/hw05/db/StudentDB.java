package hr.fer.zemris.java.tecaj.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * When started, program reads the data from current directory from file
 * "database.txt" and creates database of the student records. Allows user to
 * enter query until "exit" is entered (case insensitive). Query must start with
 * the keyword "query" and then conditional expressions separated by the keyword
 * "and". Furthermore, filters database of the student records and prints table
 * of student records satisfying the query if there are some and number of those
 * records.
 * <p>
 * 
 * (e.g. <i>query jmbag > "0000000001" and firstName = "Ante"</i>).
 * 
 * @author Filip Karacic
 *
 */
public class StudentDB {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments represented by an array of {@code String}
	 */
	public static void main(String[] args) {
		List<String> lines;

		try {
			lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Error reading from \"database.txt\" in the current directory.");
			return;
		}

		StudentDatabase students = new StudentDatabase(lines);

		try (Scanner sc = new Scanner(System.in)) {
			querys(students, sc);
		}

		System.out.println("Goodbye!");
	}

	/**
	 * Allows user to enter a query and prints the result of that query process.
	 * 
	 * @param students
	 *            database of all of the student records
	 * @param sc
	 *            text scanner
	 */
	private static void querys(StudentDatabase students, Scanner sc) {
		System.out.print("> ");
		String line = sc.nextLine();
		QueryParser parser;

		while (!line.equalsIgnoreCase("exit")) {
			line = line.trim();

			if (line.isEmpty() || line.length() < 5 || !line.substring(0, 5).equals("query")) {
				System.out.println("Query must start with the keyword 'query'. Was: " + line);
				System.out.print("> ");
				line = sc.nextLine();
				continue;
			}

			try {
				parser = new QueryParser(line.substring(6).trim());
			} catch (Exception e) {
				System.out.println("Invalid query. Was: " + line);
				System.out.print("> ");
				line = sc.nextLine();
				continue;
			}

			if (parser.isDirectQuery()) {
				directQuery(students, parser);
			} else {

				try {
					List<StudentRecord> studentRecords = students.filter(new QueryFilter(parser.getQuery()));

					if (studentRecords.size() > 0) {
						notDirectQuery(studentRecords, parser, students);
					} else {
						System.out.println("Records selected: 0.");
					}
				} catch (Exception e) {
					System.out.println("Invalid query. " + e.getMessage() + " Was: " + line);
				}
			}
			System.out.print("> ");
			line = sc.nextLine();
		}

	}

	/**
	 * Processes query that is direct and prints the table of that student record if
	 * there is one.
	 * 
	 * @param students
	 *            database of all of the student records
	 * @param parser
	 *            parser for the query
	 */
	private static void directQuery(StudentDatabase students, QueryParser parser) {
		System.out.println("Using index for record retrieval.");
		StudentRecord student = students.forJMBAG(parser.getQueriedJMBAG());

		if (student != null) {
			framePrint(student.getLastName().length(), student.getFirstName().length());
			printStudentRecord(student, student.getLastName().length(), student.getFirstName().length());
			framePrint(student.getLastName().length(), student.getFirstName().length());

			System.out.println("Records selected: 1.");
		} else {
			System.out.println("Records selected: 0.");
		}
	}

	/**
	 * Processes query that is not direct and prints the table of the student
	 * records
	 * 
	 * @param studentRecords
	 *            list of the student records
	 * @param parser
	 *            parser for the query
	 * @param students
	 *            database of all of the student records
	 */
	private static void notDirectQuery(List<StudentRecord> studentRecords, QueryParser parser,
			StudentDatabase students) {
		int maxFirstName = 0;
		int maxLastName = 0;

		for (StudentRecord record : studentRecords) {
			if (record.getFirstName().length() > maxFirstName) {
				maxFirstName = record.getFirstName().length();
			}

			if (record.getLastName().length() > maxLastName) {
				maxLastName = record.getLastName().length();
			}
		}

		framePrint(maxLastName, maxFirstName);

		int a = maxLastName;
		int b = maxFirstName;

		students.filter(new QueryFilter(parser.getQuery())).forEach(record -> printStudentRecord(record, a, b));

		framePrint(a, b);
		System.out.println("Records selected: " + studentRecords.size() + ".");

	}

	/**
	 * Prints frame for the table of the student records to system output.
	 * 
	 * @param maxLastName
	 *            length of the longest last name of the student records
	 * @param maxFirstName
	 *            length of the longest first name of the student records
	 */
	private static void framePrint(int maxLastName, int maxFirstName) {
		System.out.print("+============+");

		for (int i = 1; i <= maxLastName + 2; i++) {
			System.out.print("=");
		}
		System.out.print("+");

		for (int i = 1; i <= maxFirstName + 2; i++) {
			System.out.print("=");
		}
		System.out.print("+===+\n");
	}

	/**
	 * Prints record of the given student.
	 * 
	 * @param student
	 *            record of the student
	 * @param maxLastName
	 *            length of the longest last name of the student records
	 * @param maxFirstName
	 *            length of the longest first name of the student records
	 */
	private static void printStudentRecord(StudentRecord student, int maxLastName, int maxFirstName) {
		System.out.format("| %-10s | %-" + maxLastName + "s | %-" + maxFirstName + "s | %-1s |\n", student.getJmbag(),
				student.getLastName(), student.getFirstName(), student.getFinalGrade());
	}

}
