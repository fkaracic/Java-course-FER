package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Test;
import org.junit.Assert;

public class LexerTest {

	@Test
	public void testEmptyNextToken() {
		Lexer lexer = new Lexer("");
		
		Assert.assertNotNull(lexer.nextToken());
	}

	@Test(expected=NullPointerException.class)
	public void testNull() {
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		Assert.assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		Assert.assertEquals(token, lexer.getToken());
		Assert.assertEquals(token, lexer.getToken());
	}

	@Test(expected=LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		lexer.nextToken();
		lexer.nextToken();
	}


	@Test
	public void testTextOnly() {
		Lexer lexer = new Lexer("This text.");

		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void validTextEscape() {
		Lexer lexer = new Lexer("Escaping valid \\\\ \\{$=$}");
		
		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		Assert.assertEquals("Escaping valid \\ {$=$}", lexer.getToken().getValue().asText());

	}
	
	@Test(expected=LexerException.class)
	public void invalidTextEscape() {
		Lexer lexer = new Lexer("Escaping invalid \\");  // this is three spaces and a single backslash -- 4 letters string

		lexer.nextToken();
	}
	
	@Test
	public void testMultipartInput() {
		Lexer lexer = new Lexer("This is an example. \n {$=i$}");

		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.TAGSTART, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.TAGEND, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testforLoopArguments() {
		Lexer lexer = new Lexer("{$FOR 1 2 3 \"12\" i 4 5 $}");
		
		Assert.assertEquals(TokenType.TAGSTART, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.NUMBER, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.NUMBER, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.NUMBER, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.STRING, lexer.nextToken().getType());
	
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.NUMBER, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.NUMBER, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.TAGEND, lexer.nextToken().getType());
		
		Assert.assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
}

