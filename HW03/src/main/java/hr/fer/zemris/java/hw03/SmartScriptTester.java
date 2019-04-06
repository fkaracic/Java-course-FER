package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * {@code SmartScriptTester} demonstrates work of {@code SmartScriptParser}
 * class. It parses a document and then from the given structure recreates an
 * original textual output.
 * 
 * @author Filip Karacic
 *
 */
public class SmartScriptTester {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code String}
	 * @throws IOException
	 *             if an I/O error occurs reading from the stream
	 */
	public static void main(String[] args) throws IOException {
		String docBody = new String(Files.readAllBytes(Paths.get("src/test/resources/doc1.txt")),
				StandardCharsets.UTF_8);
		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
	}

	/**
	 * Returns string representation of the entire original document. Returned
	 * {@code String} can be parsed over and over again.
	 * 
	 * @param document
	 *            entire document.
	 * @return {@code String} representation of {@code document}
	 * @throws NullPointerException
	 *             if {@code document} is {@code null}
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		Objects.requireNonNull(document);
		StringBuilder string = new StringBuilder();

		string.append(processChildren(document));

		return string.toString();
	}

	/**
	 * Returns {@code String} representing an entire document.
	 * 
	 * @param node
	 *            node of the document
	 * @return {@code String} representing an entire document
	 */
	private static String processChildren(Node node) {
		Objects.requireNonNull(node);

		StringBuilder build = new StringBuilder();

		for (int i = 0, h = node.numberOfChildren(); i < h; i++) {
			Node child = node.getChild(i);
			if (child instanceof TextNode) {
				String add = ((TextNode) child).getText();
				if (add.contains("\\")) {
					add = add.replaceAll("\\\\", "\\\\\\\\");
				}
				if (add.contains("{")) {
					add = add.replaceAll("\\{", "\\\\\\{");
				}
				build.append(add);
			} else {
				build.append("{$");
				if (child instanceof ForLoopNode) {

					build.append(" FOR " + ((ForLoopNode) child).getVariable().asText());

					build.append(" " + checkIfString(((ForLoopNode) child).getStartExpression()));
					build.append(" " + checkIfString(((ForLoopNode) child).getEndExpression()));

					if (((ForLoopNode) child).getStepExpression() != null) {
						build.append(" " + checkIfString(((ForLoopNode) child).getStepExpression()));
					}
					
					build.append(" $}");
					build.append(processChildren(child));
					build.append("{$END$}");
				} else if (child instanceof EchoNode) {
					build.append("= ");

					for (Element element : ((EchoNode) child).getElements()) {
						if (element instanceof ElementString) {
							build.append("\"" + element.asText() + "\" ");
						} else {
							build.append(element.asText() + " ");
						}
					}

					build.append("$}");
				} else {
					build.append(((TextNode) child).getText());
				}
			}
		}

		return build.toString();
	}

	/**
	 * Returns an original {@code String} representation of this element.
	 * 
	 * @param element
	 *            element representing an expression
	 * @return {@code String} of {@code element} as it was in original document.
	 */
	private static String checkIfString(Element element) {
		StringBuilder string = new StringBuilder();

		if (element instanceof ElementString) {
			string.append("\"" + element.asText() + "\"");
		} else {
			string.append(element.asText());
		}

		return string.toString();
	}
}
