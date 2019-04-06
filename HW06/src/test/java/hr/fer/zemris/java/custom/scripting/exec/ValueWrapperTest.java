package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValueWrapperTest {
	
	@Test
	public void nullAddition() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.add(wrapper2.getValue());
		
		assertEquals(0, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test
	public void nullSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertEquals(0, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test
	public void nullMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertEquals(0, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void nullDivison() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test
	public void nullComparison() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		int result = 0;
		
		assertEquals(result, wrapper1.numCompare(wrapper2.getValue()));
	}
	
	@Test
	public void integerAddition() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		wrapper1.add(wrapper2.getValue());
		
		assertEquals(8, wrapper1.getValue());
		assertEquals(5, wrapper2.getValue());
	}
	
	@Test
	public void integerSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertEquals(-2, wrapper1.getValue());
		assertEquals(5, wrapper2.getValue());
	}
	
	@Test
	public void integerMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertEquals(15, wrapper1.getValue());
		assertEquals(5, wrapper2.getValue());
	}
	
	@Test
	public void integerDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(2));
		
		wrapper1.divide(wrapper2.getValue());
		
		assertEquals(2, wrapper1.getValue());
		assertEquals(2, wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void integerZeroDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(0));
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test
	public void integerComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		assertEquals(0, wrapper1.numCompare(wrapper2.getValue()));
	}
	
	@Test
	public void integerComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void integerComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(9));
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void doubleAddition() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.14));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(5.16));
		
		wrapper1.add(wrapper2.getValue());
		
		double result = 3.14 + 5.16;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(5.16, wrapper2.getValue());
	}
	
	@Test
	public void doubleSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.14));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(1.14));
		
		wrapper1.subtract(wrapper2.getValue());
		
		double result = 3.14 - 1.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(1.14, wrapper2.getValue());
	}
	
	@Test
	public void doubleMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(5.4));
		
		wrapper1.multiply(wrapper2.getValue());
		
		double result = 3.2 * 5.4;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(5.4, wrapper2.getValue());
	}
	
	@Test
	public void doubleDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(5.0));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(2.0));
		
		wrapper1.divide(wrapper2.getValue());
		double result = 5.0 / 2.0;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(2.0, wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void doubleZeroDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.0));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(0));
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test
	public void doubleComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.125));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(3.125));
		
		assertEquals(0, wrapper1.numCompare(wrapper2.getValue()));
	}
	
	@Test
	public void doubleComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(3.14));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(5.4));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void doubleComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper(Double.valueOf(9.65));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(9.64));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void integerAndNullAddition() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.add(wrapper2.getValue());
		
		assertEquals(3, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test
	public void integerAndNullSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertEquals(3, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test
	public void integerAndNullMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertEquals(0, wrapper1.getValue());
		assertNull(wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void integerAndNullDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test
	public void integerAndNullComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(0));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		assertEquals(0, wrapper1.numCompare(wrapper2.getValue()));
	}
	
	@Test
	public void integerAndNullComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(-3));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void integerAndNullComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(9));
		ValueWrapper wrapper2 = new ValueWrapper(null);
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void integerAndDoubleAddition() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(3.14));
		
		wrapper1.add(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 + 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(3.14, wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(3.14));
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 - 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(3.14, wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(3.14));
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 * 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(3.14, wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleDivision() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(2.0));
		
		wrapper1.divide(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 5 / 2.0;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals(2.0, wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(3.0));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) == 0);
	}
	
	@Test
	public void integerAndDoubleComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(-3));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(-2.5));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void integerAndDoubleComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper(Integer.valueOf(9));
		ValueWrapper wrapper2 = new ValueWrapper(Double.valueOf(8.9));
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void stringAsIntegerAddition() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("4");
		
		wrapper1.add(wrapper2.getValue());
		
		assertEquals(7, wrapper1.getValue());
		assertEquals("4", wrapper2.getValue());
	}
	
	@Test
	public void stringAsIntegerSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("4");
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertEquals(-1, wrapper1.getValue());
		assertEquals("4", wrapper2.getValue());
	}
	
	@Test
	public void stringAsIntegerMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("4");
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertEquals(12, wrapper1.getValue());
		assertEquals("4", wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void stringAsIntegerZeroDivison() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("0");
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test 
	public void stringAsIntegerDivison() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("2");
		
		wrapper1.divide(wrapper2.getValue());
		
		assertEquals(2, wrapper1.getValue());
		assertEquals("2", wrapper2.getValue());
	}
	
	@Test
	public void stringAsIntegerComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("5");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) == 0);
	}
	
	@Test
	public void stringAsIntegerComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("8");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void stringAsIntegerComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper("12");
		ValueWrapper wrapper2 = new ValueWrapper("8");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}

	@Test
	public void stringAsDoubleAddition() {
		ValueWrapper wrapper1 = new ValueWrapper("3.5");
		ValueWrapper wrapper2 = new ValueWrapper("4.4");
		
		wrapper1.add(wrapper2.getValue());
		
		double result = 3.5 + 4.4;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("4.4", wrapper2.getValue());
	}
	
	@Test
	public void stringAsDoubleSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper("3.5");
		ValueWrapper wrapper2 = new ValueWrapper("4.4");
		
		wrapper1.subtract(wrapper2.getValue());
		
		double result = 3.5 - 4.4;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("4.4", wrapper2.getValue());
	}
	
	@Test
	public void stringAsDoubleMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper("3.3");
		ValueWrapper wrapper2 = new ValueWrapper("4.0");
		
		wrapper1.multiply(wrapper2.getValue());
		
		double result = 3.3 * 4.0;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("4.0", wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void stringAsDoubleZeroDivison() {
		ValueWrapper wrapper1 = new ValueWrapper("3.0");
		ValueWrapper wrapper2 = new ValueWrapper("0.0");
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test 
	public void stringAsDoubleDivison() {
		ValueWrapper wrapper1 = new ValueWrapper("5.0");
		ValueWrapper wrapper2 = new ValueWrapper("2.0");
		
		wrapper1.divide(wrapper2.getValue());

		double result = 5.0 / 2.0;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("2.0", wrapper2.getValue());
	}
	
	@Test
	public void stringAsDoubleComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper("5.0");
		ValueWrapper wrapper2 = new ValueWrapper("5.0");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) == 0);
	}
	
	@Test
	public void stringAsDoubleComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper("4.99");
		ValueWrapper wrapper2 = new ValueWrapper("5.0");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void stringAsDoubleComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper("5.0");
		ValueWrapper wrapper2 = new ValueWrapper("4.999");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void integerAndDoubleStringAddition() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14");
		
		wrapper1.add(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 + 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14", wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleStringSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14");
		
		wrapper1.subtract(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 - 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14", wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleStringMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14");
		
		wrapper1.multiply(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 3.0 * 3.14;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14", wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleStringDivision() {
		ValueWrapper wrapper1 = new ValueWrapper("5");
		ValueWrapper wrapper2 = new ValueWrapper("2.0");
		
		wrapper1.divide(wrapper2.getValue());
		
		assertTrue(wrapper1.getValue() instanceof Double);
		
		double result = 5 / 2.0;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("2.0", wrapper2.getValue());
	}
	
	@Test
	public void integerAndDoubleStringComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("3.0");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) == 0);
	}
	
	@Test
	public void integerAndDoubleStringComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper("3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void integerAndDoubleStringComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper("4");
		ValueWrapper wrapper2 = new ValueWrapper("3.14");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
	@Test
	public void doubleScientificNotationAddition() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E1");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		wrapper1.add(wrapper2.getValue());
		
		double result = 3.2E1 + 3.14E2;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14E2", wrapper2.getValue());
	}
	
	@Test
	public void doubleScientificNotationSubtraction() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E1");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		wrapper1.subtract(wrapper2.getValue());
		
		double result = 3.2E1 - 3.14E2;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14E2", wrapper2.getValue());
	}
	
	@Test
	public void doubleScientificNotationMultiplication() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E1");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		wrapper1.multiply(wrapper2.getValue());
		
		double result = 3.2E1 * 3.14E2;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14E2", wrapper2.getValue());
	}
	
	@Test
	public void doubleScientificNotationDivision() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		wrapper1.divide(wrapper2.getValue());
		
		double result = 3.2E3 / 3.14E2;
		
		assertEquals(result, wrapper1.getValue());
		assertEquals("3.14E2", wrapper2.getValue());
	}
	
	@Test (expected = ArithmeticException.class)
	public void doubleScientificNotationZeroDivision() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E1");
		ValueWrapper wrapper2 = new ValueWrapper("0.0E0");
		
		wrapper1.divide(wrapper2.getValue());
	}
	
	@Test
	public void doubleScientificNotationComparisonTrue() {
		ValueWrapper wrapper1 = new ValueWrapper("3.14E2");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		assertEquals(0, wrapper1.numCompare(wrapper2.getValue()));
	}
	
	@Test
	public void doubleScientificNotationComparisonLess() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E2");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E3");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) < 0);
	}
	
	@Test
	public void doubleScientificNotationComparisonGreater() {
		ValueWrapper wrapper1 = new ValueWrapper("3.2E3");
		ValueWrapper wrapper2 = new ValueWrapper("3.14E2");
		
		assertTrue(wrapper1.numCompare(wrapper2.getValue()) > 0);
	}
	
}
