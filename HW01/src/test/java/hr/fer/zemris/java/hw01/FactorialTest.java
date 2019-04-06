package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

public class FactorialTest {

	@Test(expected = IllegalArgumentException.class)
	public void negativeValue() {
		int n = -5;

		Factorial.factorial(n);
	}

	@Test
	public void singleDigit() {
		int n = 5;
		long expected = 120;

		Assert.assertEquals(expected, Factorial.factorial(n));
	}

	@Test
	public void lessThanTwentyOne() {
		int n = 18;
		long expected = 6402373705728000L;

		Assert.assertEquals(expected, Factorial.factorial(n));
	}

	@Test(expected = IllegalArgumentException.class)
	public void higherThanTwenty() {
		int n = 21;

		Factorial.factorial(n);
	}
}