package hr.fer.zemris.java.hw02;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {

	@Test
	public void onlyRealPart() {
		double real = 3.0;
		Complex expected = new Complex(3.0, 0.0);

		Assert.assertEquals(expected, Complex.fromReal(real));
	}

	@Test
	public void onlyImaginaryPart() {
		double imaginary = -5.0;
		Complex expected = new Complex(0.0, -5.0);

		Assert.assertEquals(expected, Complex.fromImaginary(imaginary));
	}

	@Test
	public void realAndImaginaryPart() {
		double real = 3.0;
		double imaginary = -5.0;
		Complex expected = new Complex(3.0, -5.0);

		Assert.assertEquals(expected.getReal(), real, Complex.DELTA);
		Assert.assertEquals(expected.getImaginary(), imaginary, Complex.DELTA);
	}

	@Test
	public void fullString() {
		String complex = "1-i";
		Complex expected = new Complex(1.0, -1.0);

		Assert.assertEquals(expected, Complex.parse(complex));
	}
	
	@Test
	public void onlyRealString() {
		String complex = "2";
		Complex expected = new Complex(2, 0.0);

		Assert.assertEquals(expected, Complex.parse(complex));
	}
	
	@Test
	public void onlyImaginaryString() {
		String complex = "-i";
		Complex expected = new Complex(0.0, -1.0);

		Assert.assertEquals(expected, Complex.parse(complex));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void plusSignString() {
		String complex = "++";
		
		Complex.parse(complex);
	}

	@Test (expected = IllegalArgumentException.class)
	public void plusSignsString() {
		String complex = "++-";
		
		Complex.parse(complex);
	}
	
	@Test
	public void fromStringReversedOrder() {
		String complex = "2.5i-3";
		Complex expected = new Complex(-3.0, 2.5);

		Assert.assertEquals(expected, Complex.parse(complex));
	}

	@Test
	public void morePartsString() {
		String complex = "2.5-3i+1-1.5i";
		double real = 2.5 + 1.0;
		double imaginary = -3.0 - 1.5;

		Complex expected = new Complex(real, imaginary);

		Assert.assertEquals(expected, Complex.parse(complex));
	}

	@Test(expected = IllegalArgumentException.class)
	public void notAllowedString() {
		String wrong = "Complex";

		Complex.parse(wrong);
	}

	@Test(expected = IllegalArgumentException.class)
	public void multipleSignsString() {
		String invalid = "2.5++3i";

		Complex.parse(invalid);
	}

	@Test(expected = NullPointerException.class)
	public void nullAsString() {
		String wrong = null;

		Complex.parse(wrong);
	}

	@Test
	public void magnitudeAndAngle() {
		Complex expected = new Complex(2.0, 3.0);
		double magnitude = expected.getMagnitude();
		double angle = expected.getAngle();

		Assert.assertEquals(expected, Complex.fromMagnitudeAndAngle(magnitude, angle));
	}

	@Test
	public void onlyImaginaryPartAngle() {
		double imaginary = 1.0;
		Complex complex = Complex.fromImaginary(imaginary);

		Assert.assertEquals(Math.PI / 2, complex.getAngle(), Complex.DELTA);
	}

	@Test
	public void addition() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = new Complex(15.5, 8.75);
		Complex expected = new Complex(17.5, 5.75);

		Assert.assertEquals(expected, c1.add(c2));
	}

	@Test(expected = NullPointerException.class)
	public void additionNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.add(c2);
	}

	@Test
	public void subtraction() {
		Complex c1 = new Complex(0.0, -3.0);
		Complex c2 = new Complex(-1.5, 6.55);
		Complex expected = new Complex(1.5, -9.55);

		Assert.assertEquals(expected, c1.sub(c2));

	}

	@Test(expected = NullPointerException.class)
	public void subtractionNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.sub(c2);
	}

	@Test
	public void multiplying() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = new Complex(1.0, 4.0);

		double expectedMagnitude = c1.getMagnitude() * c2.getMagnitude();
		double expectedAngle = c1.getAngle() + c2.getAngle();

		Complex expected = Complex.fromMagnitudeAndAngle(expectedMagnitude, expectedAngle);

		Assert.assertEquals(expected, c1.mul(c2));
	}

	@Test(expected = NullPointerException.class)
	public void multyplyingNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.mul(c2);
	}

	@Test
	public void divison() {
		Complex c1 = new Complex(2.0, 0.0);
		Complex c2 = new Complex(0.0, 5.0);

		double expectedMagnitude = c1.getMagnitude() / c2.getMagnitude();
		double expectedAngle = c1.getAngle() - c2.getAngle();

		Complex expected = Complex.fromMagnitudeAndAngle(expectedMagnitude, expectedAngle);

		Assert.assertEquals(expected, c1.div(c2));
	}

	@Test(expected = NullPointerException.class)
	public void divisionNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.div(c2);
	}

	@Test
	public void powering() {
		int n = 3;
		Complex c1 = new Complex(2.0, -3.0);

		double expectedMagnitude = Math.pow(c1.getMagnitude(), n);
		double expectedAngle = c1.getAngle() * n;

		Complex expected = Complex.fromMagnitudeAndAngle(expectedMagnitude, expectedAngle);

		Assert.assertEquals(expected, c1.power(n));
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePowering() {
		int n = -2;
		Complex c1 = new Complex(4.5, -2.0);

		c1.power(n);
	}

	@Test
	public void zeroPowering() {
		int n = 0;
		Complex c1 = new Complex(4.5, -2.0);
		Complex expected = new Complex(1.0, 0.0);

		Assert.assertEquals(expected, c1.power(n));
	}

	@Test
	public void onePowering() {
		int n = 1;
		Complex c1 = new Complex(4.5, -2.0);
		Complex expected = c1;

		Assert.assertEquals(expected, c1.power(n));
	}

	@Test
	public void rooting() {
		int n = 3;
		Complex c1 = new Complex(2.0, -3.0);
		Complex[] result = c1.root(n);

		double expectedMagnitude = Math.pow(c1.getMagnitude(), 1.0 / n);

		for (int i = 0; i < n; i++) {
			double expectedAngle = (c1.getAngle() + 2 * Math.PI * i) / n;
			Complex expected = Complex.fromMagnitudeAndAngle(expectedMagnitude, expectedAngle);

			Assert.assertEquals(expected, result[i]);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeRooting() {
		int n = -1;
		Complex c1 = new Complex(2.0, -3.0);

		c1.root(n);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroRooting() {
		int n = 0;
		Complex c1 = new Complex(2.0, -3.0);

		c1.root(n);
	}

	@Test
	public void oneRooting() {
		int n = 1;
		Complex c1 = new Complex(2.0, -3.0);
		Complex expected = c1;

		Complex[] result = c1.root(n);
		int expectedResults = 1;

		Assert.assertEquals(expectedResults, result.length);
		Assert.assertEquals(expected, result[0]);
	}
}
