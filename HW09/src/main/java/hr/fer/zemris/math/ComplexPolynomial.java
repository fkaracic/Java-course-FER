package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Defines a complex polynomial f(z) = zn*z^n+z(n-1)*z^(n-1)+...+z2*z^2+z1*z+z0
 * where z0,z1,...,zn are coefficients within the corresponding exponent of z.
 * Degree of this polynomial function is n.
 * 
 * @author Filip Karacic
 **/
public class ComplexPolynomial {
	/**
	 * Coefficients within the corresponding exponent
	 */
	Complex[] factors;

	/**
	 * Initializes newly created {@code ComplexPolynomial} object representing
	 * complex polynomial.
	 * 
	 * @param factors
	 *            coefficients within the corresponding exponent
	 * 
	 * @throws NullPointerException
	 *             if {@code factors} is null
	 * @throws IllegalArgumentException
	 *             if {@code factors} is an empty array
	 */
	public ComplexPolynomial(Complex... factors) {
		addFactors(Objects.requireNonNull(factors));

		if (this.factors.length == 0)
			throw new IllegalArgumentException("At least one factor must be given.");
	}

	/**
	 * Adds non-null given factors to the array of coefficients for this polynomial.
	 * 
	 * @param factors
	 *            coefficients within the corresponding exponent
	 */
	private void addFactors(Complex[] factors) {
		List<Complex> result = new ArrayList<>();

		for (Complex c : factors) {
			if (c != null) {
				result.add(c);
			}
		}

		this.factors = result.toArray(new Complex[0]);
	}

	/**
	 * Returns order (degree) of this complex polynomial. Degree is represented by
	 * the greatest exponent in the polynomial. E.g. for (7+2i)z^3+2z^2+5z+1 degree
	 * is 3.
	 * 
	 * @return order (degree) of this complex polynomial.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies this complex polynomial with the given complex polynomial and
	 * returns the new complex polynomial representing result of the multiplication.
	 * 
	 * @param p
	 *            the other complex polynomial
	 * 
	 * @return result of the multiplication of this polynomial with the given
	 *         polynomial
	 * 
	 * @throws NullPointerException
	 *             if {@code p} is <code>null</code>
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);

		Complex[] result = new Complex[this.factors.length + p.factors.length - 1];

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				if (result[i + j] != null) {
					result[i + j] = result[i + j].add(this.factors[i].multiply(p.factors[j]));
				} else {
					result[i + j] = (this.factors[i].multiply(p.factors[j]));
				}
			}
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Computes first derivative of this complex polynomial and returns new complex
	 * polynomial representing the result of the first derivation.
	 * 
	 * @return first derivative of this polynomial
	 **/
	public ComplexPolynomial derive() {

		Complex[] result = new Complex[factors.length - 1];

		for (int i = 1; i < factors.length; i++) {
			Complex c = factors[i];
			result[i - 1] = new Complex(c.getReal() * i, c.getImaginary() * i);
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Calculates value of this polynomial function for the given argument.
	 * 
	 * @param z
	 *            argument of this polynomial function f(z)
	 * @return value of the function polynomial function f(z)
	 * 
	 * @throws NullPointerException
	 *             if {@code z} is <code>null</code>
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);

		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		int order = factors.length - 1;

		StringJoiner join = new StringJoiner("+");

		join.add("(" + (factors[order]) + ")" + "z^" + order);

		for (int i = order - 1; i >= 0; i--) {
			if (factors[i].equals(Complex.ZERO))
				continue;

			join.add(i == 1 ? "(" + (factors[i]) + ")z"
					: i == 0 ? "(" + (factors[i]) + ")" : "(" + (factors[i]) + ")z^" + i);
		}

		return join.toString();
	}

}
