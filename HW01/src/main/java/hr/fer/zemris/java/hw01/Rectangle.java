package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * {@code Rectangle} offers calculating area and perimeter of rectangle.
 * 
 * @author Filip Karacic
 *
 */
public class Rectangle {

	/**
	 * Calculates and prints the area and the perimeter of rectangle. Provides
	 * entering rectangle's width and height from command line or keyboard.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code string}
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			try (Scanner scanner = new Scanner(System.in)) {
				double a = keyboardInput(scanner, "dužinu");
				double b = keyboardInput(scanner, "visinu");

				printRectangle(a, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (args.length == 2) {
			try {
				double a = Double.parseDouble(args[0]);
				double b = Double.parseDouble(args[1]);

				if (a <= 0 || b <= 0) {
					printMessage("Dužina i visina moraju biti pozitivni brojevi!");
				} else {
					printRectangle(a, b);
				}
			} catch (NumberFormatException e) {
				printMessage("Stranica pravokutnika mora biti broj!");
			}
		} else {
			printMessage("Krivi broj argumenata! Unesite samo dužinu i visinu pravokutnika.");
		}
	}

	/**
	 * Provides entering the length of one side of the rectangle (width or height).
	 * Action is repeated until user enters a valid length.
	 * 
	 * @param scanner
	 *            text scanner that reads user's inputs
	 * @param side
	 *            name of the rectangle's side (width or height)
	 * 
	 * @return length of the side
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code scanner} is {@code null}
	 */
	public static double keyboardInput(Scanner scanner, String side) throws IllegalArgumentException {
		if (scanner == null) {
			throw new IllegalArgumentException("Krivo zadan argument. Scanner ne smije biti null!");
		}

		double d;

		while (true) {
			System.out.printf("Unesite " + side + " > ");
			String text = scanner.nextLine();

			try {
				d = Double.parseDouble(text);

				if (d < 0) {
					printMessage("Unijeli ste negativnu vrijednost.");
				} else if (d == 0) {
					printMessage("Unijeli ste nulu.");
				} else {
					break;
				}

			} catch (NumberFormatException e) {
				printMessage("\'" + text + "\'" + " se ne može protumačiti kao broj.");
			}
		}
		return d;
	}

	/**
	 * Prints given {@code string} to the standard output system.
	 * 
	 * @param message
	 *            {@code string} to be printed
	 */
	public static void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Prints rectangle's characteristics.
	 * 
	 * @param a
	 *            width of rectangle. {@code a} must be greater than 0.
	 * @param b
	 *            height of rectangle. {@code b} must be greater than 0.
	 */
	public static void printRectangle(double a, double b) {
		if (a <= 0 || b <= 0) {
			System.out.println("Neispravan pravokutnik. Dužina i visina pravokutnika moraju biti veći od 0.");
		} else {
			System.out.printf("Pravokutnik dužine %.1f i visine %.1f " + "ima površinu %.1f i opseg %.1f.\n", a, b,
					area(a, b), perimeter(a, b));
		}
	}

	/**
	 * Calculates area of the rectangle.
	 * 
	 * @param a
	 *            width of the rectangle. {@code a} must be greater than 0.
	 * @param b
	 *            height of the rectangle. {@code b} must be greater than 0.
	 * 
	 * @return area of the rectangle
	 * 
	 * @throws IllegalArgumentException
	 *             if rectangle's width or height is equal or less than 0.
	 */
	public static double area(double a, double b) throws IllegalArgumentException {
		if (a <= 0 || b <= 0) {
			throw new IllegalArgumentException("Dužina i visina pravokutnika moraju biti veći od 0.");
		}

		return a * b;
	}

	/**
	 * Calculates perimeter of the rectangle.
	 * 
	 * @param a
	 *            width of the rectangle. {@code a} must be greater than 0.
	 * @param b
	 *            height of the rectangle. {@code b} must be greater than 0.
	 * 
	 * @return perimeter of the rectangle
	 * 
	 * @throws IllegalArgumentException
	 *             if width or height of the rectangle is equal or less than 0.
	 */
	public static double perimeter(double a, double b) throws IllegalArgumentException {
		if (a <= 0 || b <= 0) {
			throw new IllegalArgumentException("Dužina i visina pravokutnika moraju biti veći od 0.");
		}

		return 2 * a + 2 * b;
	}
}
