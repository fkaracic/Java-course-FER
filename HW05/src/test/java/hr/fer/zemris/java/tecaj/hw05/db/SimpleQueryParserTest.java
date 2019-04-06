package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryParser.SimpleQueryParserException;

public class SimpleQueryParserTest {

	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		new SimpleQueryParser(null);
	}

	@Test
	public void testEmpty() {
		SimpleQueryParser parser = new SimpleQueryParser("");
		
		Assert.assertTrue(parser.getQuery().isEmpty());
	}

	
	@Test
	public void testLegalInput() {
		SimpleQueryParser parser = new SimpleQueryParser("jmbag = \"0000000003\"");
		
		Assert.assertFalse(parser.getQuery().isEmpty());
	}
	
	@Test (expected = SimpleQueryParserException.class)
	public void testIllegalStringLiteral() {
		new SimpleQueryParser("jmbag = \"0000000003");
	}
	
	@Test (expected = SimpleQueryParserException.class)
	public void testIllegalOperator() {
		new SimpleQueryParser("jmbag => \"0000000003");
	}
	
	@Test (expected = SimpleQueryParserException.class)
	public void testIllegalVariable() {
		new SimpleQueryParser("1jmbag = \"0000000003");
	}
	
}
