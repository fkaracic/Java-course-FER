package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class ComparisonOperatorsTest {
	private IComparisonOperator LESS = ComparisonOperators.LESS;
	private IComparisonOperator LEQ = ComparisonOperators.LESS_OR_EQUALS;
	private IComparisonOperator GREATER = ComparisonOperators.GREATER;
	private IComparisonOperator GEQ = ComparisonOperators.GREATER_OR_EQUALS;
	private IComparisonOperator EQUALS = ComparisonOperators.EQUALS;
	private IComparisonOperator NEQ = ComparisonOperators.NOT_EQUALS;
	private IComparisonOperator LIKE = ComparisonOperators.LIKE;
	
	@Test
	public void lessFalseBecauseEquals() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertFalse(LESS.satisfied(first, second));
	}
	
	@Test
	public void lessFalseBecauseGreater() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertFalse(LESS.satisfied(first, second));
	}
	
	@Test
	public void lessTrue() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertTrue(LESS.satisfied(first, second));
	}
	
	@Test
	public void lessOrEqualsTrueBecauseLess() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertTrue(LEQ.satisfied(first, second));
	}
	
	@Test
	public void lessOrEqualsTrueBecauseEquals() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertTrue(LEQ.satisfied(first, second));
	}
	
	@Test
	public void lessOrEqualsFalse() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertFalse(LEQ.satisfied(first, second));
	}
	
	@Test
	public void greaterFalseBecauseEquals() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertFalse(GREATER.satisfied(first, second));
	}
	
	@Test
	public void greaterFalseBecauseLess() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertFalse(GREATER.satisfied(first, second));
	}
	
	@Test
	public void greaterTrue() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertTrue(GREATER.satisfied(first, second));
	}
	
	@Test
	public void greaterOrEqualsTrueBecauseGreater() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertTrue(GEQ.satisfied(first, second));
	}
	
	@Test
	public void greaterOrEqualsTrueBecauseEquals() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertTrue(GEQ.satisfied(first, second));
	}
	
	@Test
	public void greaterOrEqualsFalse() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertFalse(GEQ.satisfied(first, second));
	}
	
	@Test
	public void equalsFalseBecauseLess() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertFalse(EQUALS.satisfied(first, second));
	}
	
	@Test
	public void equalsFalseBecauseGreater() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertFalse(EQUALS.satisfied(first, second));
	}
	
	@Test
	public void equalsTrue() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertTrue(EQUALS.satisfied(first, second));
	}
	
	@Test
	public void notEqualsTrueBecauseLess() {
		String first = "Iva";
		String second = "Ivan";
		
		Assert.assertTrue(NEQ.satisfied(first, second));
	}
	
	@Test
	public void notEqualsTrueBecauseGreater() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertTrue(NEQ.satisfied(first, second));
	}
	
	@Test
	public void notEqualsFalse() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertFalse(NEQ.satisfied(first, second));
	}
	
	@Test
	public void likeEquals() {
		String first = "Ivan";
		String second = "Ivan";
		
		Assert.assertTrue(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeNotEquals() {
		String first = "Ivana";
		String second = "Ivan";
		
		Assert.assertFalse(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeTrueWildCardAtTheBeggining() {
		String first = "Ivan";
		String second = "*an";
		
		Assert.assertTrue(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeFalseWildCardAtTheBeggining() {
		String first = "Ivan";
		String second = "*en";
		
		Assert.assertFalse(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeTrueWildCardAtTheEnd() {
		String first = "Ivan";
		String second = "Iv*";
		
		Assert.assertTrue(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeFalseWildCardAtTheEnd() {
		String first = "Ivan";
		String second = "Ic*";
		
		Assert.assertFalse(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeTrueWildCardInTheMiddle() {
		String first = "Ivan";
		String second = "I*n";
		
		Assert.assertTrue(LIKE.satisfied(first, second));
	}
	
	@Test
	public void likeFalseWildCardInTheMiddle() {
		String first = "Ivan";
		String second = "I*en";
		
		Assert.assertFalse(LIKE.satisfied(first, second));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void likeMultipleWildCard() {
		String first = "Ivan";
		String second = "I*n*";
		
		LIKE.satisfied(first, second);
	}
	
}
