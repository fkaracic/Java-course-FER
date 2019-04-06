package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.structures.ArrayIndexedCollection;
import hr.fer.zemris.java.hw03.structures.EmptyStackException;
import hr.fer.zemris.java.hw03.structures.ObjectStack;

/**
 * 
 * {@code SmartScriptParser} represents parser with the following rules.
 * <p>
 * Spaces in tags are ignorable. {$END$} means the same as {$ END $}. Each tag
 * has its name. The name of {$ FOR … $} tag is FOR, and the name of {$= … $}
 * tag is =. Tag names are case-insensitive. This means that you can write {$
 * FOR … $} or {$ For … $} or {$ foR … $} or similar. A one or more spaces
 * (tabs, enters or spaces – we will treat them equally) can be included before
 * tag name, so all of the following is also OK: {$FOR … $} or {$ FOR … $} or {$
 * FOR … $}. =-tag is an empty tag – it has no content so it does not need
 * closing tag. FOR-tag, however, is not an empty tag. Its has content and an
 * accompanying END-tag must be present to close it.
 * <p>
 * Tag FOR can have three or four parameters (as specified by user): first it
 * must have one variable and after that two or three elemnts of type variable,
 * number or string.
 * <p>
 * Valid variable name starts by letter and after follows zero or more letters,
 * digits or underscores. Valid function name starts with @ after which follows
 * a letter and after than can follow zero or more letters, digits or
 * underscores. Valid operators are + (plus), - (minus), * (multiplication), /
 * (division), ^ (power). Valid tag names are “=”, or variable name. So = is
 * valid tag name (but not valid variable name).
 * 
 * 
 * @author Filip Karacic
 *
 */
public class SmartScriptParser {
	/**
	 * Lexer used for tokenizaton.
	 */
	private Lexer lexer;
	/**
	 * Stack used for data structuring.
	 */
	private ObjectStack stack;

	/**
	 * Initializes newly created {@code SmartScriptParser} object representing a
	 * parser of this text.
	 * 
	 * @param text
	 *            text to be parsed
	 * @throws SmartScriptParserException
	 *             if invalid text was given for parsing.
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}
	 */
	public SmartScriptParser(String text) {
		Objects.requireNonNull(text);

		lexer = new Lexer(text);
		stack = new ObjectStack();

		stack.push(new DocumentNode());
		parse();
	}

	/**
	 * Returns node representing the entire document.
	 * 
	 * @return node representing the entire document.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.peek();
	}

	/**
	 * Parses the given text using lexer for lexical analysis.
	 * 
	 * @throws SmartScriptParserException
	 *             if invalid text is given.
	 */
	private void parse() {

		try {
			while (true) {
				Token token = lexer.nextToken();

				if (token.getType() == TokenType.EOF) {
					break;
				}

				else if (token.getType() == TokenType.TEXT) {
					Node node = ((Node) stack.pop());
					node.addChildNode(new TextNode(token.getValue().asText()));
					stack.push(node);
				}

				else if (token.getType() == TokenType.TAGSTART) {
					token = lexer.nextToken();

					if (token.getType() != TokenType.VARIABLE)
						throw new SmartScriptParserException("After tag is opened with '{$' tag name was expected.");

					if (token.getValue().asText().toUpperCase().equals("END")) {
						token = lexer.nextToken();
						if (token.getType() != TokenType.TAGEND)
							throw new SmartScriptParserException("'END' tag is invalid. Was not closed properly.");

						stack.pop();

					}

					else if (token.getValue().asText().toUpperCase().equals("FOR")) {
						forLoopCheck(token);

					} else if (token.getValue().asText().equals("=")) {
						emptyTagCheck(token);

					} else {
						throw new SmartScriptParserException(
								"Invalid tag name. 'FOR' and '=' are the only allowed. Was: "
										+ token.getValue().asText());
					}
				} else {
					throw new SmartScriptParserException("Cannot parse this text. Invalid order. '"
							+ token.getValue().asText() + "' cannot be here");
				}
			}
		} catch (LexerException e) {
			throw new SmartScriptParserException("Text cannot be parsed.", e);
		} catch (EmptyStackException e) {
			throw new SmartScriptParserException("'END' tag should be used to close only non-empty tags.");
		}

		if (stack.size() != 1)
			throw new SmartScriptParserException("Invalid text. Every non-empty tag must be closed.");
	}

	private void emptyTagCheck(Token token) {
		token = lexer.nextToken();

		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		while (token.getType() != TokenType.TAGEND && token.getType() != TokenType.EOF) {
			switch (token.getType()) {
			case VARIABLE:
			case FUNCTION:
			case OPERATOR:
			case STRING:
			case NUMBER:
				elements.add(token.getValue());
				break;
			default:
				throw new SmartScriptParserException(
						"Empty tag should only contained variables, functions, operators, strings or numbers.");
			}
			token = lexer.nextToken();
		}

		Element[] elem = new Element[elements.size()];
		int i = 0;
		for (Object el : elements.toArray()) {
			elem[i++] = (Element) el;
		}
		EchoNode echo = new EchoNode(elem);

		Node node = ((Node) stack.pop());
		node.addChildNode(echo);
		stack.push(node);
		
	}

	private void forLoopCheck(Token token) {
		token = lexer.nextToken();

		if (token.getType() != TokenType.VARIABLE)
			throw new SmartScriptParserException(
					"Variable name was expected after tag name. Was: " + token.getValue().asText());

		ElementVariable variable = (ElementVariable) token.getValue();

		ArrayIndexedCollection forLoop = new ArrayIndexedCollection();
		token = lexer.nextToken();

		while (token.getType() != TokenType.TAGEND) {
			if (token.getType() != TokenType.NUMBER && token.getType() != TokenType.STRING && token.getType() != TokenType.VARIABLE)
				throw new SmartScriptParserException(
						"'FOR' tag argument must be number, variable or string.");

			forLoop.add(token.getValue());
			token = lexer.nextToken();
		}

		if (forLoop.size() < 2 || forLoop.size() > 3)
			throw new SmartScriptParserException(
					"'FOR' tag must contain variable name and two or three arguments.");

		Node node = (Node) stack.pop();
		ForLoopNode loop;

		if (forLoop.size() == 2) {
			loop = new ForLoopNode(variable, (Element) forLoop.get(0), (Element) forLoop.get(1), null);
		} else {
			loop = new ForLoopNode(variable, (Element) forLoop.get(0), (Element) forLoop.get(1),
					(Element) forLoop.get(2));
		}
		node.addChildNode(loop);
		stack.push(node);
		stack.push(loop);
		
	}

}