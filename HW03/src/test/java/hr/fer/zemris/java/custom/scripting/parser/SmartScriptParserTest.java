package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	public void testValid() {
		String document = loader("doc1.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		Assert.assertNotNull(doc);
		
		int docChildren = 4;
		int forLoopChildren1 = 3;
		int forLoopChildren2 = 5;
		
		Assert.assertEquals(docChildren, doc.numberOfChildren());
		
		Assert.assertTrue(doc.getChild(0) instanceof TextNode);
		Assert.assertTrue(doc.getChild(1) instanceof ForLoopNode);
		Assert.assertTrue(doc.getChild(2) instanceof TextNode);
		Assert.assertTrue(doc.getChild(3) instanceof ForLoopNode);
		
		Assert.assertEquals(forLoopChildren1, doc.getChild(1).numberOfChildren());
		Assert.assertEquals(forLoopChildren2, doc.getChild(3).numberOfChildren());
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void higherNumberOfEndTags() {
		String document = loader("doc2.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void lowerNumberOfEndTags() {
		String document = loader("doc3.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test
	public void validEscapingInDocumentText() {
		String document = loader("doc4.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		String text = ((TextNode)doc.getChild(0)).getText();
		
		Assert.assertEquals("This is \\ sample text. {$=$}\r\n", text);
	}
	
	@Test
	public void validEscapingInString() {
		String document = loader("doc16.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		ForLoopNode node = ((ForLoopNode)doc.getChild(0));
		
		Assert.assertEquals("\\2\"3", node.getStartExpression().asText());
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void invalidEscapingInString() {
		String document = loader("doc17.txt");
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void invalidEscapingInDocumentText() {
		String document = loader("doc5.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test
	public void nestedForLoopText() {
		String document = loader("doc7.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		int docChildren = 4; // nested for-loop is not child of the DocumentNode but of upper for-loop
		
		Assert.assertEquals(docChildren, doc.numberOfChildren());
	}
	
	@Test
	public void emptyDocument() {
		String document = loader("doc8.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		int docChildren = 0;
		
		Assert.assertNotNull(doc);
		Assert.assertEquals(docChildren, doc.numberOfChildren());
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		new SmartScriptParser(null);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void lowArgumentsForLoop() {
		String document = loader("doc9.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void highArgumentsForLoop() {
		String document = loader("doc10.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void notProperlyClosedTag() {
		String document = loader("doc11.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void invalidTagName() {
		String document = loader("doc12.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void invalidVariableName() {
		String document = loader("doc13.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test
	public void negativeNumber() {
		String document = loader("doc14.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		String expected = "-2";
		ForLoopNode loop = (ForLoopNode) doc.getChild(1);
		
		Assert.assertEquals(expected, ((ElementConstantInteger)loop.getStartExpression()).asText());
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void invalidFunctionName() {
		String document = loader("doc15.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void functioninAForLoop() {
		String document = loader("doc18.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void InvalidOperator() {
		String document = loader("doc19.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void InvalidENDTag() {
		String document = loader("doc20.txt");
		
		new SmartScriptParser(document);
	}
	
	@Test
	public void majorSpacesinDocument() {
		String document = loader("doc21.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		Assert.assertNotNull(doc);
		
		int docChildren = 4;
		int forLoopChildren1 = 3;
		int forLoopChildren2 = 5;
		
		Assert.assertEquals(docChildren, doc.numberOfChildren());
		
		Assert.assertTrue(doc.getChild(0) instanceof TextNode);
		Assert.assertTrue(doc.getChild(1) instanceof ForLoopNode);
		Assert.assertTrue(doc.getChild(2) instanceof TextNode);
		Assert.assertTrue(doc.getChild(3) instanceof ForLoopNode);
		
		Assert.assertEquals(forLoopChildren1, doc.getChild(1).numberOfChildren());
		Assert.assertEquals(forLoopChildren2, doc.getChild(3).numberOfChildren());
	}
	
	@Test
	public void caseInsensitiveTagNames() {
		String document = loader("doc22.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode doc = parser.getDocumentNode();
		
		Assert.assertNotNull(doc);
		
		int docChildren = 4;
		int forLoopChildren1 = 3;
		int forLoopChildren2 = 5;
		
		Assert.assertEquals(docChildren, doc.numberOfChildren());
		
		Assert.assertTrue(doc.getChild(0) instanceof TextNode);
		Assert.assertTrue(doc.getChild(1) instanceof ForLoopNode);
		Assert.assertTrue(doc.getChild(2) instanceof TextNode);
		Assert.assertTrue(doc.getChild(3) instanceof ForLoopNode);
		
		Assert.assertEquals(forLoopChildren1, doc.getChild(1).numberOfChildren());
		Assert.assertEquals(forLoopChildren2, doc.getChild(3).numberOfChildren());
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void tagNesting() {
		String document = loader("doc23.txt");
		
		new SmartScriptParser(document);
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}