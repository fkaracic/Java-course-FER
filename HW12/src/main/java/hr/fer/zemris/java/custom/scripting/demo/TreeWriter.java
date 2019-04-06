package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Represents tree writer of the document node.
 * 
 * @author Filip Karacic
 *
 */
public class TreeWriter {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("I was expecting single argument: file name");
			return;
		}

		try {
			String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);

			SmartScriptParser p = new SmartScriptParser(docBody);

			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch (SmartScriptParserException e) {
			System.out.println("Error parsing file.");
		} catch (IOException e) {
			System.out.println("Error reading files.");
		}
	}

	/**
	 * Represents implementation of visitor of nodes.
	 *
	 */
	public static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			String add = node.getText();
			if (add.contains("\\")) {
				add = add.replaceAll("\\\\", "\\\\\\\\");
			}
			if (add.contains("{")) {
				add = add.replaceAll("\\{", "\\\\\\{");
			}

			System.out.print(add);

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder build = new StringBuilder();

			build.append("{$");

			build.append(" FOR " + node.getVariable().asText());

			build.append(" " + checkIfString(node.getStartExpression()));
			build.append(" " + checkIfString(node.getEndExpression()));

			if (node.getStepExpression() != null) {
				build.append(" " + node.getStepExpression().asText());
			}

			build.append(" $}");
			System.out.print(build.toString());

			int size = node.numberOfChildren();

			for (int i = 0; i < size; i++) {
				node.getChild(i).accept(this);
			}

			System.out.print("{$END$}");

		}

		/**
		 * Appends quotation marks if given element is {@code ElementString} object.
		 * 
		 * @param element element to be checked
		 * 
		 * @return string representation of the element
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

		@Override
		public void visitEchoNode(EchoNode node) {
			StringBuilder build = new StringBuilder();
			build.append("{$");

			build.append("= ");

			for (Element element : node.getElements()) {
				if (element instanceof ElementString) {
					build.append("\"" + element.asText() + "\" ");
				} else {
					build.append(element.asText() + " ");
				}
			}

			build.append("$}");

			System.out.print(build.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int size = node.numberOfChildren();

			for (int i = 0; i < size; i++) {
				node.getChild(i).accept(this);
			}

		}

	}
}
