package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * {@code EchoNode} represents a node representing a command which generates
 * some textual output dynamically.
 * 
 * @author Filip Karacic
 *
 */
public class EchoNode extends Node {
	/**
	 * array of the elements in this node.
	 */
	private Element[] elements;

	/**
	 * Initializes newly created object representing a command which generates some
	 * textual output dynamically.
	 * 
	 * @param elements
	 *            array of elements in this node.
	 * @throws NullPointerException
	 *             if {@code elements} is {@code null}
	 */
	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements);
		this.elements = Arrays.copyOf(elements, elements.length);
	}

	/**
	 * Returns elements of this node.
	 * 
	 * @return all of the elements of this node
	 */
	public Element[] getElements() {
		return elements;
	}
	
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
