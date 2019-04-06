package hr.fer.zemris.math;

import org.junit.Test;

import java.util.List;

import org.junit.Assert;

public class ComplexTest {

	@Test
	public void realAndImaginaryPart() {
		double real = 3.0;
		double imaginary = -5.0;
		Complex expected = new Complex(3.0, -5.0);

		Assert.assertEquals(expected.getReal(), real, Complex.DELTA);
		Assert.assertEquals(expected.getImaginary(), imaginary, Complex.DELTA);
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
		
		Complex expected = new Complex(2 + 12, 8 - 3);

		Assert.assertEquals(expected, c1.multiply(c2));
	}

	@Test(expected = NullPointerException.class)
	public void multyplyingNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.multiply(c2);
	}

	@Test
	public void divison() {
		Complex c1 = new Complex(2.0, 0.0);
		Complex c2 = new Complex(0.0, 5.0);

		Complex expected = new Complex(0, -2./5);

		Assert.assertEquals(expected, c1.divide(c2));
	}

	@Test(expected = NullPointerException.class)
	public void divisionNull() {
		Complex c1 = new Complex(2.0, -3.0);
		Complex c2 = null;

		c1.divide(c2);
	}

	@Test
	public void powering() {
		int n = 2;
		Complex c1 = new Complex(2.0, -3.0);

		Complex expected = new Complex(4 - 9, -12);

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
		Complex expected = new Complex(4.5, -2.0);

		Assert.assertEquals(expected, c1.power(n));
	}

	@Test
	public void rooting() {
		int n = 3;
		Complex c1 = new Complex(0.0, 1.0);
		List<Complex> result = c1.root(n);
		
		double sqrtOfThree = Math.sqrt(3);
		
		Complex[] expected = new Complex[] {new Complex(sqrtOfThree / 2, 0.5), new Complex(-sqrtOfThree / 2, 0.5), new Complex(0.0, -1.0)};

		for (int i = 0; i < n; i++) {
			Assert.assertEquals(expected[i], result.get(i));
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
		Complex expected = new Complex(2.0, -3.0);

		List<Complex> result = c1.root(n);
		int expectedResults = 1;

		Assert.assertEquals(expectedResults, result.size());
		Assert.assertEquals(expected, result.get(0));
	}
}
