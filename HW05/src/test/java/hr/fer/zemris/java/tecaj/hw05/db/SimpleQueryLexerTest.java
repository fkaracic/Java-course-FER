package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryLexer.SimpleQueryLexerException;
import hr.fer.zemris.java.tecaj.hw05.db.SimpleQueryLexer.SimpleQueryTokenType;

import org.junit.Assert;

public class SimpleQueryLexerTest {

	@Test
	public void testNotNull() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("");
		
		Assert.assertNotNull(lexer.nextToken());
	}

	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		new SimpleQueryLexer(null);
	}

	@Test
	public void testEmpty() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("");
		
		Assert.assertEquals(SimpleQueryTokenType.EOF, lexer.nextToken().getTokenType());
	}

	@Test(expected=SimpleQueryLexerException.class)
	public void testRadAfterEOF() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("");

		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testLegalInput() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("jmbag = \"0000000003\"");
		
		Assert.assertEquals(SimpleQueryTokenType.VARIABLE, lexer.nextToken().getTokenType());
		Assert.assertEquals(SimpleQueryTokenType.OPERATOR, lexer.nextToken().getTokenType());
		Assert.assertEquals(SimpleQueryTokenType.STRING, lexer.nextToken().getTokenType());
		Assert.assertEquals(SimpleQueryTokenType.EOF, lexer.nextToken().getTokenType());
	}
	
	@Test (expected = SimpleQueryLexerException.class)
	public void testIllegalStringLiteral() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("jmbag = \"0000000003");
		
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test (expected = SimpleQueryLexerException.class)
	public void testIllegalOperator() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("jmbag => \"0000000003");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test (expected = SimpleQueryLexerException.class)
	public void testIllegalVariable() {
		SimpleQueryLexer lexer = new SimpleQueryLexer("1jmbag = \"0000000003");
		
		lexer.nextToken();
	}
}
