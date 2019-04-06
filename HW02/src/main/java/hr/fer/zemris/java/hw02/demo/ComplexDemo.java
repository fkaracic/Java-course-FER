package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.Complex;

/**
 * Program {@code ComplexDemo} works with {@code ComplexNumber} class. It
 * creates three complex numbers by using methods from {@code ComplexNumber}
 * class.
 * 
 * @author Filip Karacic
 *
 */
public class ComplexDemo {

	/**
	 * Method that is called when program starts.
	 * 
	 * @param args command line arguments represented as array of {@code String}
	 */
	public static void main(String[] args) {
		Complex c1 = new Complex(2, 3);
		Complex c2 = Complex.parse("2.5-3i");
		Complex c3 = c1.add(Complex.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
