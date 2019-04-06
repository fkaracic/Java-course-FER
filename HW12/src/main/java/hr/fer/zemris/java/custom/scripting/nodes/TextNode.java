package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * {@code TextNode} is used to represent a node representing a piece of textual
 * data.
 * 
 * @author Filip Karacic
 *
 */
public class TextNode extends Node {
	/**
	 * Textual data of this node.
	 */
	private String text;

	/**
	  * Initialize newly created {@code TextNode} object so that it
	 * represents textual data.
	 * 
	 * @param text textual data.
	 */
	public TextNode(String text) {
		this.text = Objects.requireNonNull(text);
	}

	/**
	 * Return textual data of this node as {@code String}.
	 * 
	 * @return textual data as {@code String}
	 */
	public String getText() {
		return text;
	}
	
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
