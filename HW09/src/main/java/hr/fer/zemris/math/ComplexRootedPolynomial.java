package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

/**
 * Defines a complex polynomial f(z) = (z-z1)*(z-z2)*...*(z-zn), where z1,
 * z2,..., zn, are zeros of a function f(z) and are complex numbers. Degree of
 * this polynomial is n.
 * 
 * @author Filip Karacic
 */
public class ComplexRootedPolynomial {
	/**
	 * Zeros of this polynomial function.
	 */
	Complex[] roots;

	/**
	 * Initializes newly created {@code ComplexRootedPolynomial} object representing
	 * polynomial with the complex roots.
	 * 
	 * @param roots
	 *            Zeros of the polynomial function
	 * 
	 * @throws NullPointerException
	 *             if {@code roots} is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if {@code roots} is an empty array
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = Arrays.copyOf(Objects.requireNonNull(roots), roots.length);

		if (this.roots.length == 0 || this.roots[0] == null)
			throw new IllegalArgumentException("At least one zero of a function must be given.");
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

		Complex result = Complex.ONE;

		for (Complex c : roots) {
			result = result.multiply(z.sub(c));
		}

		return result;
	}

	/**
	 * Creates complex polynomial with the form: f(z) =
	 * zn*z^n+z(n-1)*z^(n-1)+...+z2*z^2+z1*z+z0. z0,z1,...,zn are coefficients
	 * within the corresponding exponent of z.
	 * 
	 * @return complex polynomial in the given form
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);

		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}

		return result;
	}

	/**
	 * Finds index of the closest root for given complex number {@code z} that is
	 * within treshold; if there is no such root, returns -1
	 * 
	 * @param z
	 *            fixed complex number
	 * @param treshold
	 *            max acceptable distance
	 * 
	 * @return index of the closest root for {@code z} that is within treshold
	 * 
	 * @throws NullPointerException
	 *             if {@code z} is <code>null</code>
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		Objects.requireNonNull(z);
	
		double minDistance = treshold + 1;
		int index = -1;
	
		for (int i = 0; i < roots.length; i++) {
			double distance = sqrt(
					pow(roots[i].getReal() - z.getReal(), 2) + pow(roots[i].getImaginary() - z.getImaginary(), 2));
			if (distance <= treshold && distance < minDistance) {
				index = i;
				minDistance = distance;
			}
		}
	
		return index;
	}

	@Override
	public String toString() {

		StringJoiner join = new StringJoiner("*");

		for (int i = 0; i < roots.length; i++) {

			String text = roots[i].equals(Complex.ZERO) ? "z"
					: roots[i].negate().toString().startsWith("-") ? "(z" + roots[i].negate() + ")"
							: "(z+" + roots[i].negate() + ")";
			join.add(text);
		}

		return join.toString();
	}
}