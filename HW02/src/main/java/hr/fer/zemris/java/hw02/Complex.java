package hr.fer.zemris.java.hw02;

import java.util.Objects;
import static java.lang.Math.abs;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Represents immutable complex numbers in a standard form. Complex number has
 * real part and imaginary part. Class {@code ComplexNumber} also contains the
 * {@code DELTA} that defines equality of the two complex numbers. Class
 * {@code ComplexNumber} contains methods for working with complex numbers (e.g.
 * calculating angle or magnitude, adding, subtracting, multiplying, dividing
 * two complex numbers).
 * 
 * @author Filip Karacic
 *
 */
public class Complex {
	/**
	 * Real part of the complex number.
	 */
	private double real;
	/**
	 * Imaginary part of the complex number.
	 */
	private double imaginary;
	/**
	 * {@code DELTA} defines equality of the two complex numbers.
	 */
	public static final double DELTA = 1E-8;

	/**
	 * Initialize a newly created {@code ComplexNumber} object that represents a
	 * complex number in standard form. Once created complex number is immutable.
	 * 
	 * @param real real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns the real part of this complex number.
	 * 
	 * @return real part of this complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of this complex number.
	 * 
	 * @return imaginary part of this complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates the magnitude of this complex number.
	 * 
	 * @return magnitude of this complex number.
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Calculates the angle of this complex number. Returns the angle in [0, 2π].
	 * 
	 * @return angle of this complex number
	 */
	public double getAngle() {
		if (imaginary < 0) {
			return atan2(imaginary, real) + 2 * PI;
		} else {
			return atan2(imaginary, real);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(real, imaginary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Complex)) return false;

		Complex other = (Complex) obj;

		if (abs(this.real - other.real) > DELTA || abs(this.imaginary - other.imaginary) > DELTA) {
			return false;
		}

		return true;
	}

	/**
	 * Returns {@code ComplexNumber} object represented as a complex number
	 * containing only real part defined by {@code real}. Imaginary part is 0.
	 * 
	 * @param real real part of this complex number.
	 * @return {@code ComplexNumber} object with real part equal to {@code real}
	 */
	public static Complex fromReal(double real) {
		return new Complex(real, 0.0);
	}

	/**
	 * Returns {@code ComplexNumber} object represented as a complex number
	 * containing only imaginary part defined by {@code imaginary}. Real part is 0.
	 * 
	 * @param imaginary imaginary part of this complex number.
	 * @return {@code ComplexNumber} object with imaginary part equal to
	 *         {@code imaginary}
	 */
	public static Complex fromImaginary(double imaginary) {
		return new Complex(0.0, imaginary);
	}

	/**
	 * Returns {@code ComplexNumber} object represented as a complex number
	 * converted from trigonometric form to standard form. Calculates real and
	 * imaginary part from magnitude and angle of this complex number.
	 * 
	 * @param magnitude magnitude of this complex number
	 * @param angle angle of this complex number
	 * @return {@code ComplexNumber} object with real and imaginary parts calculated
	 *         from the angle and magnitude
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * cos(angle);
		double imaginary = magnitude * sin(angle);

		return new Complex(real, imaginary);
	}

	/**
	 * Returns {@code ComplexNumber} object represented by {@code String}.
	 * 
	 * @param s {@code String} that represents a complex number
	 * 
	 * @return {@code ComplexNumber} object defined by {@code String}
	 * 
	 * @throws IllegalArgumentException if {@code String} is not valid representation of a complex number
	 * 
	 */
	public static Complex parse(String s) {
		Objects.requireNonNull(s);

		s = s.replaceAll("\\s+", "");

		if (s.isEmpty() || !correct(s) || multipleSigns(s) || (signCount(s) > numbersCount(s))) {
			throw new IllegalArgumentException("Argument must be in a standard complex number form (e.g '2+3i'). Was: " + "'" + s + "'");
		}

		boolean[] negative = signs(s);

		String[] string = parsed(s);

		return toComplex(string, negative);
	}

	private static int numbersCount(String s) {
		int count = 0;
		
		for(int i = 0, h = s.length(); i < h; i++) {
			if(Character.isDigit(s.charAt(i)) || s.charAt(i) == 'i') {
				count++;
			}
		}
		
		return count;
	}

	private static int signCount(String s) {
		int count = 1; // the first one

		for (int i = 1, h = s.length(); i < h; i++) {
			if (s.charAt(i) == '-' || s.charAt(i) == '+') {
				count++;
			}
		}
		
		return count;
	}

	/**
	 * Returns {@code true} if {@code String} has multiple '+' or '-' signs in a
	 * row, {@code false} otherwise.
	 * 
	 * @param s {@code String} to be checked
	 * @return {@code true} if {@code String} has multiple '+' or '-' signs in a
	 *         row, {@code false} otherwise.
	 */
	private static boolean multipleSigns(String s) {
		if(s.length() == 1) return false;

		for (int i = 1, h = s.length(); i < h; i++) {
			if (s.charAt(i) == '-' || s.charAt(i) == '+') {
				if (s.charAt(i - 1) == '-' || s.charAt(i - 1) == '+') {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns {@code boolean} array that represents sign of every part of the
	 * complex number
	 * 
	 * @param s {@code String} that represents complex number
	 * @return {@code boolean} array that represents sign of every part of the
	 *         complex number
	 */
	private static boolean[] signs(String s) {
		int count = signCount(s);

		boolean[] negative = new boolean[count];
		int index = 0;

		if (s.charAt(0) == '-') {
			negative[index] = true;
		}

		index++;

		for (int i = 1, h = s.length(); i < h; i++) {
			if (s.charAt(i) == '-') {
				negative[index++] = true;
			} else if (s.charAt(i) == '+') {
				index++;
			}
		}

		return negative;
	}

	/**
	 * Returns {@code true} if {@code String} is a valid representation of complex
	 * number, {@code false} otherwise.
	 * 
	 * @param s {@code String} that represents complex number
	 * @return {@code true} if {@code String} is a valid representation of complex
	 *         number, {@code false} otherwise.
	 */
	private static boolean correct(String s) {
		for (int i = 0, h = s.length(); i < h; i++) {
			char c = s.charAt(i);

			if (!Character.isDigit(c) && c != '.' && c != 'i' && c != '+' && c != '-') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns array of {@code String} where each element presents one part of the
	 * complex number parsed from original {@code String}.
	 * 
	 * @param s {@code String} representing complex number
	 * @return array of {@code String} parsed from original {@code String}
	 */
	private static String[] parsed(String s) {
		return s.split("[+-]");
	}

	/**
	 * Returns {@code ComplexNumber} object created from array of {@code String}
	 * representing numeric parts of the complex number and array of {@code boolean}
	 * representing signs of that parts of the complex number. Each element of the
	 * array of {@code boolean} is {@code true} if the element at the exact same
	 * index of array of {@code String} should be negative, {@code false} if it
	 * should be positive.
	 * 
	 * @param string
	 *            array of {@code String} representing numeric parts of the complex
	 *            number
	 * @param negative
	 *            array of {@code boolean} representing signs of the {@code String}
	 *            parts of the complex number
	 * 
	 * @return {@code ComplexNumber} object representing complex number created from
	 *         {@code string} and {@code negative}
	 */
	private static Complex toComplex(String[] string, boolean[] negative) {
		double real = 0.0;
		double imaginary = 0.0;
		int negativeCount = 0;
		
		for (int i = 0; i < string.length; i++) {
			if(string[i].isEmpty()) continue;
			
			else if (string[i].contains("i")) {
				if (string[i].replace("i", "").equals("")) {
					imaginary = addition(imaginary, 1.0, negative[negativeCount++]);
				} else {
					imaginary = addition(imaginary, Double.parseDouble(string[i].replace("i", "")), negative[negativeCount++]);
				}

			} else {
				real = addition(real, Double.parseDouble(string[i]), negative[negativeCount++]);
			}
		}
		return new Complex(real, imaginary);
	}

	/**
	 * Returns value of subtraction of the two given values if {@code negative} is
	 * {@code true}, value of addition of the two given values otherwise.
	 * 
	 * @param value1 value to be subtracted from or added to
	 * @param value2 value to subtract or to add
	 * @param negative
	 *            {@code true} if operation is subtraction, {@code false} if
	 *            operation is addition
	 * 
	 * @return value of subtraction of the two given values if {@code negative} is
	 *         {@code true}, value of addition of the two given values otherwise
	 */
	private static double addition(double value1, double value2, boolean negative) {
		return negative ? value1 - value2 : value1 + value2;
	}

	/**
	 * Returns {@code ComplexNumber} object created by adding this complex number to
	 * the complex number represented by {@code c}.
	 * 
	 * 
	 * @param c complex number to be added to this complex number
	 * 
	 * @return {@code ComplexNumber} object created by adding given complex number
	 *         to this complex number
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Returns {@code ComplexNumber} object created by subtracting this complex
	 * number to the complex number represented by {@code c}.
	 * 
	 * 
	 * @param c complex number to be subtracted from this complex number
	 * 
	 * @return {@code ComplexNumber} object created by subtracting given complex
	 *         number from this complex number
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Returns {@code ComplexNumber} object created by multiplying this complex
	 * number with the complex number represented by {@code c}.
	 * 
	 * 
	 * @param c complex number to be multiplied with this complex number
	 * @return {@code ComplexNumber} object created by multiplying given complex
	 *         number with this complex number
	 */
	public Complex mul(Complex c) {
		Objects.requireNonNull(c);

		double magnitude = this.getMagnitude() * c.getMagnitude();
		double angle = this.getAngle() + c.getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns {@code ComplexNumber} object created by division of this complex
	 * number and complex number represented by {@code c}.
	 * 
	 * @param c complex number to be divided from this complex number
	 * @return {@code ComplexNumber} object created by division of the given complex
	 *         number and this complex number
	 */
	public Complex div(Complex c) {
		Objects.requireNonNull(c);

		double magnitude = this.getMagnitude() / c.getMagnitude();
		double angle = this.getAngle() - c.getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns {@code ComplexNumber} object represented by a complex number
	 * calculated from exponentiating this complex number with exponent {@code n}.
	 * 
	 * @param n degree of the exponentiation function
	 * @return {@code ComplexNumber} object represented by a complex number
	 *         calculated from powering this complex number
	 * 
	 * @throws IllegalArgumentException if {@code n} is less than 0
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n cannot be negative.");

		double magnitude = pow(getMagnitude(), n);
		double angle = n * getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns array of {@code ComplexNumber} objects represented by a complex
	 * numbers calculated as n-th root of this complex number.
	 * 
	 * @param n degree of the root
	 * 
	 * @return array of {@code ComplexNumber} objects represented by a complex
	 *         numbers calculated as n-th root of this complex number.
	 * 
	 * @throws IllegalArgumentException if {@code n} is equal or less than 0
	 */
	public Complex[] root(int n) {
		if (n <= 0)	throw new IllegalArgumentException("n must be greater than 0.");

		Complex[] result = new Complex[n];
		double magnitude = pow(getMagnitude(), 1.0 / n);

		for (int i = 0; i < n; i++) {
			double angle = (getAngle() + 2 * PI * i) / n;
			result[i] = fromMagnitudeAndAngle(magnitude, angle);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (imaginary == 0)
			return real + "";
		else if (real == 0)
			return imaginary + "i";
		else if (imaginary > 0)
			return real + " + " + imaginary + "i";
		else
			return real + " - " + -imaginary + "i";
	}
}
