package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * {@code DocumentNode} is used to represent an entire document.
 * 
 * @author Filip Karacic
 *
 */
public class DocumentNode extends Node {

	/**
	 * Initialize newly created object that represents an entire document.
	 */
	public DocumentNode() {
	}
	
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
