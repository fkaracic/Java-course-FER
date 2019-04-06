package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents visitor of the nodes. There are: text node, for-loop node,
 * echo-node and document node.
 * 
 * @author Filip Karacic
 *
 */
public interface INodeVisitor {

	/**
	 * Visits the given text node and performs specified action over it.
	 * 
	 * @param node
	 *            textual node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits the given for-loop node and performs specified action over it and its
	 * specific children.
	 * 
	 * @param node
	 *            for-loop node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits the echo node and performs specified action over its arguments.
	 * 
	 * @param node
	 *            echo node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits the given document node and calls specified visit action for each
	 * child.
	 * 
	 * @param node
	 *            textual node
	 */
	public void visitDocumentNode(DocumentNode node);
}
