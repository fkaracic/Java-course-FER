package hr.fer.zemris.math;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents immutable complex numbers in a standard form. Complex number has
 * real part and imaginary part. Class {@code Complex} also contains the
 * {@code DELTA} that defines equality of the two complex numbers. Class
 * {@code Complex} contains methods for work with complex numbers (e.g. adding,
 * subtracting, multiplying, dividing two complex numbers).
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
	 * 0+0i complex number.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * 1+0i complex number.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * -1+0i complex number.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * 0+i complex number.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * 0-i complex number.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Initialize a newly created {@code ComplexNumber} object that represents a
	 * complex number whose module is zero. Once created complex number is
	 * immutable.
	 * 
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Initialize a newly created {@code Complex} object that represents a complex
	 * number in standard form. Once created complex number is immutable.
	 * 
	 * @param re
	 *            real part of the complex number
	 * @param im
	 *            imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * Calculates the module(magnitude) of this complex number.
	 * 
	 * @return module(magnitude) of this complex number.
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns new {@code Complex} object created by multiplying this complex number
	 * with the complex number represented by {@code c}.
	 * 
	 * @param c
	 *            complex number to be multiplied with this complex number
	 * 
	 * @return {@code Complex} object created by multiplying given complex number
	 *         with this complex number
	 * 
	 * @throws NullPointerException
	 *             if the {@code c} is <code>null</code>
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Returns {@code Complex} object created by division of this complex number and
	 * complex number represented by {@code c}.
	 * 
	 * @param c
	 *            complex number to be divided from this complex number
	 * 
	 * @return {@code ComplexNumber} object created by division of the given complex
	 *         number and this complex number
	 * 
	 * @throws NullPointerException
	 *             if the {@code c} is <code>null</code>
	 */
	public Complex divide(Complex c) {
		double module = c.module();
		
		Complex conjugate = new Complex(c.real, -c.imaginary);

		return this.multiply(conjugate).multiply(new Complex(1 / (module * module), 0));
	}

	/**
	 * Returns {@code Complex} object created by adding this complex number to the
	 * complex number represented by {@code c}.
	 * 
	 * 
	 * @param c
	 *            complex number to be added to this complex number
	 * 
	 * @return {@code ComplexNumber} object created by adding given complex number
	 *         to this complex number
	 * 
	 * @throws NullPointerException
	 *             if the {@code c} is <code>null</code>
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Returns {@code Complex} object created by subtracting this complex number to
	 * the complex number represented by {@code c}.
	 * 
	 * @param c
	 *            complex number to be subtracted from this complex number
	 * 
	 * @return {@code Complex} object created by subtracting given complex number
	 *         from this complex number
	 * 
	 * @throws NullPointerException
	 *             if the {@code c} is a <code>null</code>
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Returns newly created {@code Complex} object representing negated value of
	 * this complex number.
	 * 
	 * @return newly created {@code Complex} object representing negated value of
	 * this complex number.
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns {@code Complex} object represented by a complex number calculated
	 * from exponentiating this complex number with exponent {@code n}.
	 * 
	 * @param n
	 *            degree of the exponentiation function
	 * 
	 * @return {@code Complex} object represented by a complex number calculated
	 *         from powering this complex number
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code n} is less than 0
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Given number must be non-negative integer. Was: " + n);

		double r = Math.pow(module(), n);
		double angle = angle() * n;

		return new Complex(r * Math.cos(angle), r * Math.sin(angle));
	}

	/**
	 * Returns list of {@code ComplexNumber} objects represented by a complex
	 * numbers calculated as n-th root of this complex number.
	 * 
	 * @param n
	 *            degree of the root
	 * 
	 * @return array of {@code ComplexNumber} objects represented by a complex
	 *         numbers calculated as n-th root of this complex number.
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code n} is equal or less than 0
	 */
	public List<Complex> root(int n) {
		if (n < 1)
			throw new IllegalArgumentException("Given number must be non-negative integer. Was: " + n);
		
		List<Complex> result = new ArrayList<>();

		double r = Math.pow(module(), n);
		double angle = angle();

		for (int i = 0; i < n; i++) {
			double currentAngle = (angle + 2 * Math.PI * i) / n;
			result.add(new Complex(r * Math.cos(currentAngle), r * Math.sin(currentAngle)));
		}

		return result;
	}

	/**
	 * Calculates the angle of this complex number. Returns the angle in [0, 2π].
	 * 
	 * @return angle of this complex number
	 */
	private double angle() {
		double angle = Math.atan2(imaginary, real);

		return imaginary < 0 ? angle + 2 * Math.PI : angle;
	}

	@Override
	public String toString() {
		if (imaginary == 0)
			return real + "";

		if (real == 0)
			return imaginary + "i";

		return imaginary < 0 ? real + "-" + -imaginary + "i" : real + "+" + imaginary + "i";
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
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;

		Complex other = (Complex) obj;

		return abs(this.real - other.real) < DELTA && abs(this.imaginary - other.imaginary) < DELTA;
	}

	/**
	 * Returns real part of this complex number.
	 * @return real part of this complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of this complex number.
	 * @return imaginary part of this complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	
}