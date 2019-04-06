package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.functions.DecfmtFunction;
import hr.fer.zemris.java.custom.scripting.functions.DupFunction;
import hr.fer.zemris.java.custom.scripting.functions.Function;
import hr.fer.zemris.java.custom.scripting.functions.ParamGetFunction;
import hr.fer.zemris.java.custom.scripting.functions.PparamDelFunction;
import hr.fer.zemris.java.custom.scripting.functions.PparamSetFunction;
import hr.fer.zemris.java.custom.scripting.functions.PparamgetFunction;
import hr.fer.zemris.java.custom.scripting.functions.SetMimeTypeFunction;
import hr.fer.zemris.java.custom.scripting.functions.SinFunction;
import hr.fer.zemris.java.custom.scripting.functions.SwapFunction;
import hr.fer.zemris.java.custom.scripting.functions.TparamDelFunction;
import hr.fer.zemris.java.custom.scripting.functions.TparamGetFunction;
import hr.fer.zemris.java.custom.scripting.functions.TparamSetFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents engine whose job is to actually execute the document whose parsed
 * tree it obtains. It visits the tree of the document and executes action for
 * each node.
 * 
 * @author Filip Karacic
 *
 */
public class SmartScriptEngine {

	/**
	 * Document node(i.e. parsed tree) for this engine.
	 */
	private DocumentNode documentNode;
	/**
	 * Context of the web server.
	 */
	private RequestContext requestContext;
	/**
	 * Multistack for this engine.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Constructor for initialization of the newly created {@code SmartScriptEngine}
	 * object.
	 * 
	 * @param documentNode
	 *            parsed tree structure of the document
	 * @param requestContext
	 *            context of the web server
	 * 
	 * @throws NullPointerException
	 *             if the given node or context are <code>null</code>
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = Objects.requireNonNull(documentNode);
		this.requestContext = Objects.requireNonNull(requestContext);
	}

	/**
	 * Visits the tree structure of the document for this engine and executes action
	 * for each node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Visitor of the nodes in tree structure.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(Objects.requireNonNull(node).getText());
			} catch (IOException e) {
				throw new IllegalStateException("Writing text from the text node failed.", e);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {

			String name = node.getVariable().asText();
			multistack.push(name, new ValueWrapper(node.getStartExpression().asText()));

			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			String step = node.getStepExpression().asText();

			int size = node.numberOfChildren();

			while (true) {
				ValueWrapper value = multistack.peek(name);

				if (value.numCompare(end.getValue()) > 0) {
					break;
				}

				for (int i = 0; i < size; i++) {
					node.getChild(i).accept(this);
				}

				value.add(step);
				multistack.push(name, value);

			}

			multistack.pop(name);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();

			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof ElementString || elements[i] instanceof ElementConstantDouble
						|| elements[i] instanceof ElementConstantInteger) {

					stack.push(elements[i].asText());
				} else if (elements[i] instanceof ElementVariable) {
					ValueWrapper value = multistack.peek(elements[i].asText());
					stack.push(value.getValue());
				} else if (elements[i] instanceof ElementOperator) {
					try {
						ValueWrapper value1 = new ValueWrapper(stack.pop());
						ValueWrapper value2 = new ValueWrapper(stack.pop());

						ValueWrapper result = performOperation(value1, value2, (ElementOperator) elements[i]);

						stack.push(result.getValue());
					} catch (EmptyStackException e) {

					}
				} else if (elements[i] instanceof ElementFunction) {
					Function function = createFunction((ElementFunction) elements[i], stack);

					if (function == null)
						throw new IllegalArgumentException("Invalid function.");

					function.execute();
				}
			}

			writeReversedStack(stack);

		}

		private void writeReversedStack(Stack<Object> stack) {
			Stack<Object> help = new Stack<>();

			while (!stack.isEmpty()) {
				help.push(stack.pop());
			}

			while (!help.isEmpty()) {
				try {
					requestContext.write(help.pop().toString());
				} catch (IOException e) {
					throw new IllegalStateException("Writing text from the text node failed.", e);
				}
			}
		}

		private Function createFunction(ElementFunction elementFunction, Stack<Object> stack) {
			String name = elementFunction.getName();

			switch (name) {
			case "@sin":
				return new SinFunction(stack);
			case "@decfmt":
				return new DecfmtFunction(stack);
			case "@dup":
				return new DupFunction(stack);
			case "@swap":
				return new SwapFunction(stack);
			case "@setMimeType":
				return new SetMimeTypeFunction(stack, requestContext);
			case "@paramGet":
				return new ParamGetFunction(stack, requestContext);
			case "@pparamGet":
				return new PparamgetFunction(stack, requestContext);
			case "@pparamSet":
				return new PparamSetFunction(stack, requestContext);
			case "@pparamDel":
				return new PparamDelFunction(stack, requestContext);
			case "@tparamGet":
				return new TparamGetFunction(stack, requestContext);
			case "@tparamSet":
				return new TparamSetFunction(stack, requestContext);
			case "@tparamDel":
				return new TparamDelFunction(stack, requestContext);
			default:
				return null;
			}
		}

		private ValueWrapper performOperation(ValueWrapper value1, ValueWrapper value2, ElementOperator element) {
			String operation = element.getSymbol();

			switch (operation) {
			case "+":
				value1.add(value2.getValue());
				return value1;
			case "-":
				value1.subtract(value2.getValue());
				return value1;
			case "*":
				value1.multiply(value2.getValue());
				return value1;
			case "/":
				value1.divide(value2.getValue());
				return value1;
			default:
				return null;
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, h = node.numberOfChildren(); i < h; i++) {
				node.getChild(i).accept(this);
			}

			try {
				requestContext.write("\r\n");
			} catch (IOException e) {
				throw new IllegalArgumentException("Error writing new line.", e);
			}
		}

	};
}
