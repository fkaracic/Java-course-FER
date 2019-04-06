package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program for calculating factorial of integers within the interval [1,20].
 * 
 * @author Filip Karacic
 */
public class Factorial {

	/**
	 * Asks user to enter integer within [1,20] he wants to calculate factorial for
	 * and prints the result. Program ends when user enters "kraj".
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code string}
	 */
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.printf("Unesite broj > ");
				String input = sc.nextLine();

				if (input.equals("kraj")) {
					break;
				}

				try {
					int n = Integer.parseInt(input);

					if (n < 1 || n > 20) {
						System.out.println("\'" + n + "\'" + " nije broj u dozvoljenom rasponu.");
					} else {
						long result = factorial(n);
						System.out.printf("%d! = %d\n", n, result);
					}
				} catch (NumberFormatException e) {
					System.out.println("\'" + input + "\'" + " nije cijeli broj.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Doviđenja.");
	}

	/**
	 * Calculates factorial of integers within the interval [0,20].
	 * 
	 * @param n
	 *            number whose factorial is counted. {@code n} must be in [0,20].
	 * @return calculated factorial of the given number
	 * @throws IllegalArgumentException
	 *             if given argument is not within the interval [0,20]
	 */
	public static long factorial(int n) throws IllegalArgumentException {
		if (n < 0 || n > 20) {
			String message;

			if (n < 0) {
				message = "Faktorijel se ne može računati za negativne brojeve.";
			} else {
				message = "Funkcija ne može računati faktorijele brojeva većih od 20.";
			}

			throw new IllegalArgumentException(message);
		}

		if (n == 0) {
			return 1;
		} else {
			return n * factorial(n - 1);
		}
	}
}
