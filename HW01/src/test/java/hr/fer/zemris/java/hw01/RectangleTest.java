package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

public class RectangleTest {

	@Test(expected = IllegalArgumentException.class)
	public void negativeValueArea() {
		double a = 2.0;
		double b = -3.0;

		Rectangle.area(a, b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeValuePerimeter() {
		double a = -2.0;
		double b = 3.0;

		Rectangle.perimeter(a, b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroValueArea() {
		double a = 0.0;
		double b = 5.0;

		Rectangle.area(a, b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroValuePerimeter() {
		double a = 6.0;
		double b = 0.0;

		Rectangle.perimeter(a, b);
	}

	@Test
	public void positiveValuesArea() {
		double a = 7.5;
		double b = 4.0;
		double expected = 30.0;

		Assert.assertEquals(expected, Rectangle.area(a, b), 1E-8);
	}

	@Test
	public void positiveValuesPerimeter() {
		double a = 6.0;
		double b = 5.0;
		double expected = 22.0;

		Assert.assertEquals(expected, Rectangle.perimeter(a, b), 1E-8);
	}
}