package hr.fer.zemris.java.tecaj.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class QueryParserTest {

	@Test
	public void multipleSpacesDirectQuery() {
		QueryParser parser = new QueryParser("   jmbag        =           \"0000000003\"     ");
		
		Assert.assertTrue(parser.isDirectQuery());
		Assert.assertEquals("0000000003", parser.getQueriedJMBAG());
	}
	
	@Test
	public void multipleSpacesAndQuery() {
		QueryParser parser = new QueryParser("   jmbag        =           \"0000000003\"   and      lastName  =  \"Anić\"  ");
		
		Assert.assertFalse(parser.isDirectQuery());
		
		int expectedSize = 2;
		
		Assert.assertEquals(expectedSize, parser.getQuery().size());
	}
	
	@Test
	public void AndCasesQuery() {
		QueryParser parser = new QueryParser("jmbag = \"0000000003\" aNd lastName  =  \"Anić\"  ANd firstName = \"Ante\"");
		
		Assert.assertFalse(parser.isDirectQuery());
		
		int expectedSize = 3;
		
		Assert.assertEquals(expectedSize, parser.getQuery().size());
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidVariable() {
		new QueryParser("name = \"5\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidForm() {
		new QueryParser("\"Ante\" = firstName");
	}
	
	@Test
	public void validFormNonDirect() {
		QueryParser parser = new QueryParser("firstName=\"Ante\"");
		
		Assert.assertFalse(parser.isDirectQuery());
		
		Assert.assertEquals("Ante", parser.getQuery().get(0).getStringLiteral());
		Assert.assertEquals(ComparisonOperators.EQUALS, parser.getQuery().get(0).getComparisonOperator());
		Assert.assertEquals(FieldValueGetters.FIRST_NAME, parser.getQuery().get(0).getFieldValueGetter());
	}
	
	@Test
	public void directQuery() {
		QueryParser parser = new QueryParser("jmbag   =\"0000000003\"");
		
		Assert.assertTrue(parser.isDirectQuery());
		Assert.assertEquals("0000000003", parser.getQueriedJMBAG());
	}
	
	@Test
	public void andQueryValid() {
		QueryParser parser = new QueryParser("jmbag   =\"0000000003\" and firstName = \"Ante\" ");
		
		Assert.assertFalse(parser.isDirectQuery());
		
		int expectedSize = 2;
		
		Assert.assertEquals(expectedSize, parser.getQuery().size());
	}
	
	@Test (expected = QueryParserException.class)
	public void andQueryInvalid() {
		new QueryParser("jmbag   =\"0000000003\" and");
	}
	
	@Test (expected = QueryParserException.class)
	public void andQueryMissingOperatorString() {
		new QueryParser("jmbag   =\"0000000003\" and firstName");
	}
	
	@Test (expected = QueryParserException.class)
	public void andQuerymissingString() {
		new QueryParser("jmbag   =\"0000000003\" and firstName >");
	}
	
	@Test (expected = QueryParserException.class)
	public void multipleVariables() {
		new QueryParser("jmbag  jmbag =\"0000000003\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void emptyQuery() {
		new QueryParser("");
	}
	
	@Test (expected = QueryParserException.class)
	public void multipleOperators() {
		new QueryParser("jmbag = <= \"0000000003\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void multipleStringLiterals() {
		new QueryParser("jmbag = \"0000000003\" \"0000000004\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidVariableCasing() {
		new QueryParser("FIRSTNAME = \"Ante\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidVariableCasing2() {
		new QueryParser("LASTNAME = \"Anić\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidVariableCasing3() {
		new QueryParser("JMBAG = \"0000000003\"");
	}
	
	@Test (expected = QueryParserException.class)
	public void invalidLikeCasing() {
		new QueryParser("firstName like \"A*\"");
	}
	
}
