package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class ConditionalExpressionTest {
	
	@Test (expected = NullPointerException.class)
	public void constructorArgumentNull1() {
		new ConditionalExpression(null, "Literal", ComparisonOperators.EQUALS);
	}
	
	@Test (expected = NullPointerException.class)
	public void constructorArgumentNull2() {
		new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Literal", null);
	}
	
	@Test (expected = NullPointerException.class)
	public void constructorArgumentNull3() {
		new ConditionalExpression(FieldValueGetters.FIRST_NAME, null, ComparisonOperators.EQUALS);
	}
	
	@Test
	public void validExpression() {
		ConditionalExpression expression = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Literal", ComparisonOperators.EQUALS);
		
		Assert.assertEquals("Literal", expression.getStringLiteral());
		Assert.assertEquals(FieldValueGetters.LAST_NAME, expression.getFieldValueGetter());
		Assert.assertEquals(ComparisonOperators.EQUALS, expression.getComparisonOperator());
	}

}
