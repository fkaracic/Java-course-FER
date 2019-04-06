package hr.fer.zemris.java.util;

import hr.fer.zemris.math.Complex;

/**
 * {@code Util} is used for parsing string to complex number. String must be in
 * form: 'a+bi' and can contain one or more spaces between real or imaginary
 * part and operator (+ or -), e.g. 'a + bi'.
 * 
 * @author Filip Karacic
 *
 */
public class Util {

	/**
	 * Parses the given {@code String} to complex number in form 'a+bi'.
	 * 
	 * @param line
	 *            {@code String} representing complex number in form 'a+bi'
	 * 
	 * @return {@code Complex} object representing the complex number given in the
	 *         {@code String}, or <code>null</code> if the given {@code String} is
	 *         not valid complex number.
	 */
	public static Complex stringToComplex(String line) {
		if (line == null || line.isEmpty())
			return null;

		boolean firstPositive = false;
		boolean secondPositive = false;

		if (!line.contains("-")) {
			firstPositive = true;
			secondPositive = true;
		} else if (line.startsWith("-")) {
			if (line.contains("+")) {
				secondPositive = true;
			}

			line = line.substring(1);
		} else {
			firstPositive = true;
		}

		if (line.startsWith("+")) {
			line = line.substring(1);
		}

		return toComplex(line, firstPositive, secondPositive);
	}

	/**
	 * Validates the given {@code String} and parses the given {@code String} to
	 * complex number in form 'a+bi'.
	 * 
	 * @param line
	 *            line to be parsed
	 * @param firstPositive
	 *            sign of the first number
	 * @param secondPositive
	 *            sign of the second number
	 * 
	 * @return complex number created from the given {@code String}, or
	 *         <code>null</code> if it is not valid
	 */
	private static Complex toComplex(String line, boolean firstPositive, boolean secondPositive) {
		line = line.replaceAll(" ", "");

		if (line.endsWith("-") || line.endsWith("+"))
			return null;

		String[] parts = line.split("[+-]");

		if (!validParts(parts))
			return null;

		return createComplex(parts, firstPositive, secondPositive);
	}

	/**
	 * Create complex number in form 'a+bi' from the given parts and signs.
	 * 
	 * @param parts
	 *            parts of the complex number
	 * @param firstPositive
	 *            sign of the first number
	 * @param secondPositive
	 *            sign of the second number
	 * 
	 * @return complex number created from the given {@code String}, or
	 *         <code>null</code> if it is not valid
	 */
	private static Complex createComplex(String[] parts, boolean firstPositive, boolean secondPositive) {
		try {
			double real = 0;
			double imaginary = 0;

			for (int i = 0; i < parts.length; i++) {
				if (parts[i].contains("i")) {
					String b = parts[i].replace("i", "");
					if (b.equals("")) {
						imaginary = secondPositive ? 1 : -1;
					} else {
						imaginary = secondPositive ? Double.parseDouble(b) : Double.parseDouble("-" + b);
					}

				} else {
					real = firstPositive ? Double.parseDouble(parts[i]) : Double.parseDouble("-" + parts[i]);
				}
			}

			return new Complex(real, imaginary);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Validates parts of the complex number. Validation checks: if there are less
	 * than one or more then two parts, if imaginary unit is on the right place and
	 * is there any empty parts.
	 * 
	 * @param parts
	 *            parts of the complex number
	 * 
	 * @return <code>true</code> if valid, <code>false</code> otherwise
	 */
	private static boolean validParts(String[] parts) {
		if (parts.length != 1 && parts.length != 2)
			return false;

		for (int i = 0; i < parts.length; i++) {
			if (parts[i].isEmpty())
				return false;
		}

		if (parts.length == 2 && (parts[0].contains("i") || !parts[1].contains("i")))
			return false;

		return true;
	}
}
