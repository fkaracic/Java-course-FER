package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code Node} will be used for representation of structured documents.
 * 
 * @author Filip Karacic
 *
 */
public abstract class Node {
	/**
	 * Children of this node.
	 */
	List<Node> children;

	/**
	 * Initialize newly created object representing a node of document.
	 */
	public Node() {
	}

	/**
	 * Adds node as a child of this node. {@code null} cannot be added.
	 * 
	 * @param child
	 *            node to be added as a child of this node
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);

		if (children == null) {
			children = new ArrayList<>();
		}

		children.add(child);
	}

	/**
	 * Returns number of children of this node.
	 * 
	 * @return number of children of this node
	 */
	public int numberOfChildren() {
		if (children == null)
			return 0;

		return children.size();
	}

	/**
	 * Returns child at the specified index if index is in [0,size-1].
	 * 
	 * @param index
	 *            index of the child
	 * @return the child at the specified index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if index is not in [0, size-1]
	 */
	public Node getChild(int index) {
		if (index < 0 || index >= children.size())
			throw new IndexOutOfBoundsException();

		return (Node) children.get(index);
	}

	/**
	 * Represents function of accepting node and performing specific action.
	 * 
	 * @param visitor
	 *            visitor of the nodes
	 * 
	 */
	public abstract void accept(INodeVisitor visitor);
}
